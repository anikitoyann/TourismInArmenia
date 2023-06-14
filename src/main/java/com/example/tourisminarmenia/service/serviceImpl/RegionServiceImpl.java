package com.example.tourisminarmenia.service.serviceImpl;

import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.respository.RegionRepository;
import com.example.tourisminarmenia.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        regionRepository.deleteById(id);
    }

    @Override
    public Optional<Region> findById(int id) {
        return regionRepository.findById(id);
    }
}
