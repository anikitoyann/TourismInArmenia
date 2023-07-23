package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.QItem;
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
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

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
public class ItemServiceImpl implements ItemService {
    public final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    @PersistenceContext
    private EntityManager entityManager;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    public void addItem(MultipartFile multipartFile, Item item) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            item.setPicName(fileName);
        }
        itemRepository.save(item);
    }

    @Override
    public Optional<Item> findById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public Page<Item> findAllByType(Type type, Pageable pageable) {
        return itemRepository.findAllByType(type,pageable);
    }

    public List<Item> findAllByRegionAndType(int regionId, Type type){
        return itemRepository.findAllByRegion_IdAndType(regionId,type);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public boolean existsById(int id) {
      return itemRepository.existsById(id);
    }

    @Override
    public List<ItemDto> search(int page, int size, ItemSearchDto itemSearchDto) {
        List<Item> fetch = searchItemByFilter(page, size, itemSearchDto);
        List<ItemDto> itemDtos = itemMapper.mapListToDtos(fetch);
        return itemDtos;
    } @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> findByRegion(Region region) {
        return itemRepository.findByRegion(region);
    }
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

    public Item updateItem(Item item, Optional<Item> byId) {
        Item itemDB = byId.get();
        if (item.getName() != null && !item.getName().isEmpty()) {
            itemDB.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            itemDB.setDescription(item.getDescription());
        }
        if (item.getRegion() != null) {
            itemDB.setRegion(item.getRegion());
        }
        if (item.getType() != null) {
            itemDB.setType(item.getType());
        }
        return itemDB;
    }


}
