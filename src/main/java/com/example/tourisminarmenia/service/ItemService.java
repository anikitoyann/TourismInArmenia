package com.example.tourisminarmenia.service;

import com.example.tourisminarmenia.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ItemService {
    public void addItem(MultipartFile multipartFile, Item item) throws IOException;

    public Optional<Item> findById(int id);

}
