package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    //Retrieves a list of all cars from the car repository.
    @Override
    public List<Car> findAll() {
        log.info("Executing findAll() method");
        return carRepository.findAll();
    }

    //Saves a Car object along with its associated image file to the car repository.
    @Override
    public void save(MultipartFile multipartFile, Car car) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            car.setPicName(fileName);
            log.info("File saved: {}", fileName);
        }
        carRepository.save(car);
        log.info("Car saved: {}", car);
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

    //Updates the fields of a Car object based on the provided Car object.
    public Car updateCar(Car car, Optional<Car> byId) {
        Car carDb = byId.get();
        if (car.getName() != null && !car.getName().isEmpty()) {
            carDb.setName(car.getName());
            log.info("Name updated to: {}", car.getName());
        }
        if (car.getSeats() != null && !car.getSeats().isEmpty()) {
            carDb.setSeats(car.getSeats());
            log.info("Seats updated to: {}", car.getSeats());
        }
        //carDb.setId(car.getId());
        return carDb;
    }
}
