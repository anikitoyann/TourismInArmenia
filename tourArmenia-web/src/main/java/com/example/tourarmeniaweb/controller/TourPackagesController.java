package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.*;
import com.example.tourarmeniacommon.service.CarService;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.TourService;
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
    private final TourService tourService;
    private final RegionService regionService;
    private final CarService carService;
    private final ItemService itemService;

    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String toursPage(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourService.findAll());
        return "tour";
    }

    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Region> regions = regionService.findAll();
        List<Car> cars = carService.findAll();
        List<Item> items = itemService.findAll();
        modelMap.addAttribute("regions", regions);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        return "addTours";
    }

    @PostMapping("/add")
    public String tourPackagesAdd(@ModelAttribute TourPackage tourPackages, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        tourService.addTour(tourPackages,multipartFile);
        return "redirect:/tour";
    }
    @GetMapping("/{id}")
    public String singleTourPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<TourPackage> byId = tourService.findById(id);
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
    tourService.deleteById(id);
    return "redirect:/tour";
    }}

