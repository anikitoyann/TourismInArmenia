package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.CarService;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final RegionService regionService;
    private final ItemService itemService;
    private  final CarService carService;
    private final TourPackageService tourPackageService;
    @GetMapping
    public String adminPage(){
     return "admin";
     }
    @GetMapping("/addItem")
    public String itemAddPage(ModelMap modelMap) {
        modelMap.addAttribute("regions", regionService.findAll());
        List<Type> types = Arrays.asList(Type.values());
        modelMap.addAttribute("types", types);
        return "addItem";
    }
    @PostMapping("/addItem")
    public String hotelsAdd(@ModelAttribute Item item, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        itemService.addItem(multipartFile, item);
        return "redirect:/admin";
    }
    @GetMapping("/addTour")
    public String tourAddPage(ModelMap modelMap) {
        List<Region> regions = regionService.findAll();
        List<Car> cars = carService.findAll();
        List<Item> items = itemService.findAll();
        modelMap.addAttribute("regions", regions);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        return "addTours";
    }

    @PostMapping("/addTour")
    public String tourAdd(@ModelAttribute TourPackage tourPackages, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        tourPackageService.save(multipartFile, tourPackages);
        return "redirect:/admin";
    }
    @GetMapping("/addCar")
    public String carAddPage(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.findAll());
        return "addCars";
    }
    @PostMapping("/addCar")
    public String carAdd(@ModelAttribute Car car,  @RequestParam("image") MultipartFile multipartFile) throws IOException {
        carService.save(multipartFile, car);
        return "redirect:/admin";
    }
    @GetMapping("/removeHotel")
    public String removeHotels(@RequestParam("id") int id) {
        itemService.deleteById(id);
        return "redirect:/item";
    }
    @GetMapping("/removeTour")
    public String removeTour(@RequestParam("id") int id){
        tourPackageService.deleteById(id);
        return "redirect:/tour";
    }
}
