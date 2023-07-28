package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.repository.RegionRepository;
import com.example.tourarmeniacommon.service.RegionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    //Retrieves a list of all Region entities in the database.
    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    //Deletes a Region entity with the specified ID.
    @Override
    public void deleteById(int id) {
        regionRepository.deleteById(id);
    }

    //Retrieves a Region entity with the specified ID from the database.
    @Override
    public Optional<Region> findById(int id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Region with id {} does not exist.", id);
            throw new EntityNotFoundException("Region with " + id + " id does not exists.");
        }
        return Optional.of(byId.get());
    }

    //Saves a Region entity to the database.
    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }
}
