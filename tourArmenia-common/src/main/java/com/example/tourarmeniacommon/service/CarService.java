package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    public List<Car> findAll();

    void save(MultipartFile multipartFile, Car car) throws IOException;
}
