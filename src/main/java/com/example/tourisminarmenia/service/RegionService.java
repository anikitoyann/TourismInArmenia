package com.example.tourisminarmenia.service;

import com.example.tourisminarmenia.entity.Region;

import java.util.List;
import java.util.Optional;

public interface RegionService{
    List<Region> findAll();

    void deleteById(int id);

    Optional<Region> findById(int id);
}
