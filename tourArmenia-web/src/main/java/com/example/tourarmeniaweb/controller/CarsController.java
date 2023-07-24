package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarService carService;

    @GetMapping
    public String carsPage(ModelMap modelMap) {
        List<Car> all = carService.findAll();
        modelMap.addAttribute("cars", all);

        return "cars";

    }
}
