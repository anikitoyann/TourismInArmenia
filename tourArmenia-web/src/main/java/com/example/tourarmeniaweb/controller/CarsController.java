package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
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

    private final CarRepository carsRepository;

    // Handler for HTTP GET requests with no specific mapping value
    @GetMapping
    public String carsPage(ModelMap modelMap) {
        List<Car> all = carsRepository.findAll();
        // Add the list of cars to the modelMap for rendering in the view
        modelMap.addAttribute("cars", all);

        return "cars";

    }
}
