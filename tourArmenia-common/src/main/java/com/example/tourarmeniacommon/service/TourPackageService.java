package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TourPackageService {
   public void save(MultipartFile multipartFile, TourPackage tourPackages) throws IOException;

   void deleteById(int id);

    TourPackage add(TourPackage tour);

    Optional<TourPackage> findById(int id);

    List<TourPackage> findAll();

    boolean existsById(int id);

    TourPackage updateTour(TourPackage tourPackage,Optional<TourPackage> byId);
}