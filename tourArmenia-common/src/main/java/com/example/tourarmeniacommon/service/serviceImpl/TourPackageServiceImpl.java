package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniacommon.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
            log.info("File saved: {}", fileName);
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

    @Override
    public boolean existsById(int id) {
        return tourPackagesRepository.existsById(id);
    }

    @Override
    public Page<TourPackage> findAllByPageable(Pageable pageable) {
        return tourPackagesRepository.findAll(pageable);
    }

    @Override
    public TourPackage updateTour(TourPackage tour, Optional<TourPackage> byId) {
        TourPackage tourDB = byId.get();
        if (tour.getName() != null && !tour.getName().isEmpty()) {
            log.info("Updating tour name from '{}' to '{}'", tourDB.getName(), tour.getName());
            tourDB.setName(tour.getName());
        }
        if (tour.getRegion() != null) {
            log.info("Updating tour region to: {}", tour.getRegion());
            tourDB.setRegion(tour.getRegion());
        }
        if (tour.getGroupSize() != 0) {
            log.info("Updating tour group size from '{}' to '{}'", tourDB.getGroupSize(), tour.getGroupSize());
            tourDB.setGroupSize(tour.getGroupSize());
        }
        if (tour.getCar() != null) {
            log.info("Updating tour car to: {}", tour.getCar());
            tourDB.setCar(tour.getCar());
        }
        if (tour.getItem() != null) {
            log.info("Updating tour item to: {}", tour.getItem());
            tourDB.setItem(tour.getItem());
        }
        if (tour.getPrice() != 0) {
            log.info("Updating tour price from '{}' to '{}'", tourDB.getPrice(), tour.getPrice());
            tourDB.setPrice(tour.getPrice());
        }
        if (tour.getDuration() != null && !tour.getDuration().isEmpty()) {
            log.info("Updating tour duration from '{}' to '{}'", tourDB.getDuration(), tour.getDuration());
            tourDB.setDuration(tour.getDuration());
        }
        if (tour.getStartDate() != null) {
            log.info("Updating tour start date to: {}", tour.getStartDate());
            tourDB.setStartDate(tour.getStartDate());
        }
        log.info("Tour updated: {}", tourDB);
        return tourDB;
    }

}
