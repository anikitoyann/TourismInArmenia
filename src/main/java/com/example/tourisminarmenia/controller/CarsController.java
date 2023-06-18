package com.example.tourisminarmenia.controller;

import com.example.tourisminarmenia.entity.Car;
import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.Type;
import com.example.tourisminarmenia.respository.CarsRepository;
import com.example.tourisminarmenia.respository.RegionRepository;
import com.example.tourisminarmenia.service.ItemService;
import com.example.tourisminarmenia.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarsRepository carsRepository;

    @GetMapping
    public String carsPage(ModelMap modelMap) {
        List<Car> all = carsRepository.findAll();
        modelMap.addAttribute("cars", all);

        return "cars";

    }
}
