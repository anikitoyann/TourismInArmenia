package com.example.tourisminarmenia.service.serviceImpl;

import com.example.tourisminarmenia.entity.User;
import com.example.tourisminarmenia.respository.UserRepository;
import com.example.tourisminarmenia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
