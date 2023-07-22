package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.service.CarService;
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
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Value("${upload.image.path}")
    private String imageUploadPath;
    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void save(MultipartFile multipartFile, Car car) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            car.setPicName(fileName);
        }
        carRepository.save(car);
    }

    @Override
    public Optional<Car> findById(int carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public boolean existsById(int id) {
        return carRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        carRepository.deleteById(id);
    }
}
