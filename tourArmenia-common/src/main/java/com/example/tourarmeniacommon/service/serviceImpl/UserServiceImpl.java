package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.repository.UserRepository;
import com.example.tourarmeniacommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    //Retrieves a list of all User entities from the database.
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Retrieves a User entity from the database based on the provided email.
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Saves a User entity to the database.
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
