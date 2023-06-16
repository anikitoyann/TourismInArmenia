package com.example.tourisminarmenia.service;

import com.example.tourisminarmenia.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    void save(User user);
}
