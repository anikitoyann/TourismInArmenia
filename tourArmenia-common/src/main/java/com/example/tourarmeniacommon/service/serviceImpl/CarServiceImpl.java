package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
