package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.repository.RegionRepository;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniacommon.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourPackagesRepository tourPackagesRepository;

    @Value("${upload.image.path}")
    private String imageUploadPath;
    private int id;

    @Override
    public List<TourPackage> findAll() {
        return tourPackagesRepository.findAll();
    }

    @Override
    public void save(TourPackage tourPackages, MultipartFile multipartFile) throws IOException {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            tourPackages.setPicName(fileName);
        }
        tourPackagesRepository.save(tourPackages);
    }

    @Override
    public void deleteById(int id) {
        tourPackagesRepository.deleteById(id);
    }

    @Override
    public Optional<TourPackage> findById(int id) {
        this.id = id;
        return tourPackagesRepository.findById(id);
    }
    public boolean existById(int id){
        return tourPackagesRepository.existsById(id);
    }

    @Override
    public List<TourPackage> findByRegion(Region region) {
        return tourPackagesRepository.findByRegion(region);
    }
}
