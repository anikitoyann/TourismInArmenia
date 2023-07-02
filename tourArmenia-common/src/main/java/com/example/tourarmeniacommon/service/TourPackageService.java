package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TourPackageService {
   public void save(MultipartFile multipartFile, TourPackage tourPackages) throws IOException;

   void deleteById(int id);
}
