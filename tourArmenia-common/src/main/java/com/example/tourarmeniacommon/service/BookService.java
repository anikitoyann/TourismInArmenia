package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.User;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);

    List<Book> findAll();

    List<Book> findByUser(User user);

    Optional<Book>  findByToken(String token);
}
