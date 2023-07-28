package com.example.tourarmeniacommon.service.serviceImpl;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.mapper.ItemMapper;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.service.ItemService;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    public final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    @PersistenceContext
    private EntityManager entityManager;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    //adds an Item object to the item repository along with an associated image file, if provided.
    public void addItem(MultipartFile multipartFile, Item item) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            item.setPicName(fileName);
        }
        itemRepository.save(item);
    }

    // Retrieves an Item object from the item repository based on the provided item ID.
    @Override
    public Optional<Item> findById(int id) {
        log.debug("Searching for Item with id: {}", id);
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Item with id {} does not exist.", id);
            throw new EntityNotFoundException("Item with " + id + " id does not exists.");
        }
        log.debug("Found Item with id {}: {}", id, byId.get());
        return Optional.of(byId.get());
    }

    // Retrieves a paginated list of Item objects from the item repository that match the specified Type.
    @Override
    public Page<Item> findAllByType(Type type, Pageable pageable) {
        return itemRepository.findAllByType(type,pageable);
    }

    // Retrieves a paginated list of Item objects from the item repository that belong to a specific Region and match the specified Type.
    public Page<Item> findAllByRegionAndType(Pageable pageable, int regionId, Type type){
        return itemRepository.findAllByRegion_IdAndType(pageable, regionId,type);
    }

    //Saves an Item object to the item repository.
    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    //Checks if an Item with the specified ID exists in the item repository.
    @Override
    public boolean existsById(int id) {
        return itemRepository.existsById(id);
    }

    //Performs a search for items based on the provided search criteria and pagination parameters.
    //The method fetches items that match the search criteria from the database and returns a list of ItemDto objects
    //representing the search results.
    @Override
    public List<ItemDto> search(int page, int size, ItemSearchDto itemSearchDto) {
        List<Item> fetch = searchItemByFilter(page, size, itemSearchDto);
        List<ItemDto> itemDtos = itemMapper.mapListToDtos(fetch);
        return itemDtos;
    }

    //Retrieves a paginated list of Item entities.
    @Override
    public Page<Item> findAllByPageable(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    //Deletes an Item entity with the specified ID.
    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    //Retrieves a list of all Item entities in the database.
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    //Retrieves a list of Item entities associated with the specified Region.
    @Override
    public List<Item> findByRegion(Region region) {
        return itemRepository.findByRegion(region);
    }

    // Searches for Item entities based on filtering criteria provided in the ItemSearchDto object.
  private List<Item> searchItemByFilter(int page, int size, ItemSearchDto itemSearchDto) {
       QItem qItem = QItem.item;
        var query = new JPAQuery<Item>(entityManager);
        JPAQuery<Item> from = query.from(qItem);
        if (itemSearchDto.getDescription() != null && !itemSearchDto.getDescription().isEmpty()) {
            from.where(qItem.description.contains(itemSearchDto.getDescription()));
        }
        if (itemSearchDto.getName() != null && itemSearchDto.getName().isEmpty()) {
            from.where(qItem.name.contains(itemSearchDto.getName()));
        }
        if (itemSearchDto.getRegionName() != null && itemSearchDto.getRegionName().isEmpty()) {
            from.where(qItem.region.name.contains(itemSearchDto.getRegionName()));
        }
        if (itemSearchDto.getType() != null) {
            from.where(qItem.type.eq(itemSearchDto.getType()));
        }
        if (page > 0) {
            from.offset((long) page * size) ;
        }
        from.limit(size);
        PathBuilder<Object> objectPathBuilder = new PathBuilder<Object>(Item.class, itemSearchDto.getSortBy());
        from.orderBy(new OrderSpecifier("asc".equalsIgnoreCase(itemSearchDto.getSortDirection()) ? Order.ASC
                : Order.DESC, objectPathBuilder));
        List<Item> fetch = from.fetch();
        return fetch;
    }

    //Updates the properties of an existing Item with the values from the provided Item object.
    public Item updateItem(Item item, Optional<Item> byId) {
        Item itemDB = byId.get();
        if (item.getName() != null && !item.getName().isEmpty()) {
            itemDB.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            itemDB.setDescription(item.getDescription());
            log.info("Description updated to: {}", item.getDescription());
        }
        if (item.getRegion() != null) {
            itemDB.setRegion(item.getRegion());
            log.info("Region updated to: {}", item.getRegion());
        }
        if (item.getType() != null) {
            itemDB.setType(item.getType());
            log.info("Type updated to: {}", item.getType());
        }
        log.info("Updated item: {}", itemDB);
        return itemDB;
    }


}