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
   public void save(MultipartFile multipartFile, TourPackage tourPackages) throws IOException;

   void deleteById(int id);

    TourPackage add(TourPackage tour);

    Optional<TourPackage> findById(int id);

    List<TourPackage> findAll();

    void save(TourPackage tourPackages, MultipartFile multipartFile) throws IOException;

    boolean existById(int id);

    List<TourPackage> findByRegion(Region region);
}
