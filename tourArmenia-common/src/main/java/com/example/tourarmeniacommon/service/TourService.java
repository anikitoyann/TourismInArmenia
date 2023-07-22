package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TourService {

    List<TourPackage> findAll();

    void addTour(TourPackage tourPackages, MultipartFile multipartFile) throws IOException;

    Optional<TourPackage> findById(int id);

    void deleteById(int id);


}
