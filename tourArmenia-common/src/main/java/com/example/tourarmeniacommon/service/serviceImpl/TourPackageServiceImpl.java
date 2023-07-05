package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniacommon.service.TourPackageService;
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
public class TourPackageServiceImpl implements TourPackageService {
    private final TourPackagesRepository tourPackagesRepository;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    @Override
    public void save(MultipartFile multipartFile, TourPackage tourPackage) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            tourPackage.setPicName(fileName);
        }
        tourPackagesRepository.save(tourPackage);
    }

    @Override
    public void deleteById(int id) {
        tourPackagesRepository.deleteById(id);
    }

    @Override
    public TourPackage add(TourPackage tour) {
        return tourPackagesRepository.save(tour);
    }

    @Override
    public Optional<TourPackage> findById(int id) {
        return tourPackagesRepository.findById(id);
    }

    @Override
    public List<TourPackage> findAll() {
        return tourPackagesRepository.findAll();
    }
}