package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Region;

import java.util.List;
import java.util.Optional;

public interface RegionService{
    List<Region> findAll();

    void deleteById(int id);

    Optional<Region> findById(int id);

    Region save(Region region);
}
