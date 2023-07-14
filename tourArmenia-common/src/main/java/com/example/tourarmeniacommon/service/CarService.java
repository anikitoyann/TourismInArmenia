package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CarService {
    public List<Car> findAll();

    void save(MultipartFile multipartFile, Car car) throws IOException;

    Optional<Car> findById(int carId);

    public Car create(Car car);

    boolean existsById(int id);

    void deleteById(int id);
}
