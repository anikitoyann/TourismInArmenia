package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    List<Book> findAll();
}
