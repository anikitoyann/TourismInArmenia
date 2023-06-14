package com.example.tourisminarmenia.service.serviceImpl;

import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.respository.ItemRepository;
import com.example.tourisminarmenia.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
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
}
