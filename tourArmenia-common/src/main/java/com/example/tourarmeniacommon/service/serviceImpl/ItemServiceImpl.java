package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.service.ItemService;
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
    @Value("${upload.image.path}")
    private String imageUploadPath;
    private int id;

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
        this.id = id;
        return itemRepository.findById(id);
    }

    @Override
    public Page<Item> findAllByType(Type type, Pageable pageable) {
        return itemRepository.findAllByType(type,pageable);
    }

    @Override
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
}
