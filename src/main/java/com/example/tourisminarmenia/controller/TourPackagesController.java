package com.example.tourisminarmenia.controller;

import com.example.tourisminarmenia.entity.*;
import com.example.tourisminarmenia.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tour")
public class TourPackagesController {
    private final TourPackagesRepository tourPackagesRepository;
    private final RegionRepository regionsRepository;
    private final CarsRepository carsRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String toursPage(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourPackagesRepository.findAll());
        return "tour";
    }

    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Region> regions = regionsRepository.findAll();
        List<Car> cars = carsRepository.findAll();
        List<Item> items = itemRepository.findAll();
        modelMap.addAttribute("regions", regions);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        return "addTours";
    }

    @PostMapping("/add")
    public String tourPackagesAdd(@ModelAttribute TourPackage tourPackages, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            tourPackages.setPicName(fileName);
        }
        tourPackagesRepository.save(tourPackages);
        return "redirect:/tour";
    }
    @GetMapping("/{id}")
    public String singleTourPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<TourPackage> byId = tourPackagesRepository.findById(id);
        if (byId.isPresent()) {
            TourPackage tourPackage = byId.get();
            modelMap.addAttribute("tour", tourPackage);
            return "singleTour";
        } else {
            return "redirect:/tour";
        }

    }

@GetMapping("/remove")
    public String removeTour(@RequestParam("id") int id){
    tourPackagesRepository.deleteById(id);
    return "redirect:/tour";
    }}

