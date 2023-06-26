package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cars")
public class CarEndpoint {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.findAll());
    }
}
