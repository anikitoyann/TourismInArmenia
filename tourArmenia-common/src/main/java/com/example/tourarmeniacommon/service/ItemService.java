package com.example.tourarmeniacommon.service;


import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    public void addItem(MultipartFile multipartFile, Item item) throws IOException;

    public Optional<Item> findById(int id);

    Page<Item> findAllByType(Type type, Pageable pageable);

    void deleteById(int id);

    List<Item> findAll();

    List<Item> findByRegion(Region region);

    Page<Item> findAllByRegionAndType(Pageable pageable ,int id, Type type);

    public Item save(Item item);

    boolean existsById(int id);

    List<ItemDto> search(int page, int size, ItemSearchDto itemSearchDto);

    Item updateItem(Item item, Optional<Item> byId);

    Page<Item> findAllByPageable(Pageable pageable);
}

