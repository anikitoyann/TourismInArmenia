package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TourPackageService {

   TourPackage add(TourPackage tourPackage);
   void save(MultipartFile multipartFile, TourPackage tourPackages) throws IOException;

   void deleteById(int id);

    Optional<TourPackage> findById(int id);

    List<TourPackage> findAll();

    List<TourPackage> findByRegion(Region region);
    boolean existsById(int id);

    Page<TourPackage> findAllByPageable(Pageable pageable);

    TourPackage updateTour(TourPackage tourPackage,Optional<TourPackage> byId);

}
