package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    private RegionServiceImpl regionService;
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        regionRepository = mock(RegionRepository.class);
        regionService = new RegionServiceImpl(regionRepository);
    }

    @Test
    void findAll() {
        List<Region> regions = new ArrayList<>();
        Region region1 = new Region();
        region1.setId(1);
        region1.setName("Region 1");
        region1.setRegionalCenter("Center 1");
        Region region2 = new Region();
        region2.setId(2);
        region2.setName("Region 2");
        region2.setRegionalCenter("Center 2");
        regions.add(region1);
        regions.add(region2);

        when(regionRepository.findAll()).thenReturn(regions);
        List<Region> result = regionService.findAll();
        assertEquals(regions.size(), result.size());
        assertEquals(region1.getName(), result.get(0).getName());
        assertEquals(region2.getName(), result.get(1).getName());
        assertEquals(region1.getRegionalCenter(), result.get(0).getRegionalCenter());
        assertEquals(region2.getRegionalCenter(), result.get(1).getRegionalCenter());
    }

    @Test
    void deleteById() {
        int regionIdToDelete = 1;
        regionService.deleteById(regionIdToDelete);
        verify(regionRepository, times(1)).deleteById(regionIdToDelete);
    }

    @Test
    void findById() {
        int regionIdToFind = 1;
        Region region = new Region();
        region.setId(regionIdToFind);
        region.setName("Region 1");
        region.setRegionalCenter("Center 1");
        when(regionRepository.findById(regionIdToFind)).thenReturn(Optional.of(region));
        Optional<Region> result = regionService.findById(regionIdToFind);
        assertTrue(result.isPresent());
        assertEquals(regionIdToFind, result.get().getId());
        assertEquals(region.getName(), result.get().getName());
        assertEquals(region.getRegionalCenter(), result.get().getRegionalCenter());
    }

    @Test
    void save() {
        Region regionToSave = new Region();
        regionToSave.setName("New Region");
        regionToSave.setRegionalCenter("New Center");

        Region savedRegion = new Region();
        savedRegion.setId(1);
        savedRegion.setName("New Region");
        savedRegion.setRegionalCenter("New Center");

        when(regionRepository.save(regionToSave)).thenReturn(savedRegion);
        Region result = regionService.save(regionToSave);
        assertNotNull(result);
        assertEquals(savedRegion.getId(), result.getId());
        assertEquals(savedRegion.getName(), result.getName());
        assertEquals(savedRegion.getRegionalCenter(), result.getRegionalCenter());
    }
}