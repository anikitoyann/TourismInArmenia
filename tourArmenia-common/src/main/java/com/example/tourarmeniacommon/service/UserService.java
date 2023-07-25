package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    void save(User user);

}
