package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.CarService;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final RegionService regionService;
    private final ItemService itemService;
    private final CarService carService;
    private final TourPackageService tourPackageService;

    @GetMapping
    public String adminPage(){
     return "admin";
     }
   @GetMapping("/addRegion")
         public String regionPage(){
        return "addRegion";
         }

         @PostMapping("/addRegion")
         public String addRegionPage(@ModelAttribute Region region) {
        regionService.save(region);
        return "redirect:/admin";
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
    public String carAdd(@ModelAttribute Car car, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        carService.save(multipartFile, car);
        return "redirect:/admin";
    }

    @GetMapping("/removeHotel")
    public String removeHotels(@RequestParam("id") int id) {
        itemService.deleteById(id);
        return "redirect:/item";
    }

    @GetMapping("/removeTour")
    public String removeTour(@RequestParam("id") int id) {
        tourPackageService.deleteById(id);
        return "redirect:/tour";
    }

    @GetMapping("/removeCar")
    public String removeCar(@RequestParam("id") int id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/updateCar")
    public String updateCarPage() {
        return "updateCar";
    }

    @PostMapping("/updateCar")
    public String updateCar(@RequestParam("id") int id,
                            @ModelAttribute Car car,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<Car> byId = carService.findById(id);
        if (!byId.isEmpty()) {
            Car carDb = carService.updateCar(car, byId);
            carService.save(multipartFile, carDb);
            return "redirect:/cars";
        }
        log.warn("Car update failed: Car with ID {} not found.", id);
        return "redirect:/cars";
    }

    @GetMapping("/updateTour")
    public String updateTourPage(ModelMap modelMap) {
        modelMap.addAttribute("regions", regionService.findAll());
        modelMap.addAttribute("cars", carService.findAll());
        modelMap.addAttribute("items", itemService.findAll());
        return "updateTour";
    }


    @PostMapping("/updateTour")
    public String updateTour(@RequestParam("id") int id,
                            @ModelAttribute TourPackage tourPackage,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<TourPackage> byId = tourPackageService.findById(id);
        if (!byId.isEmpty()) {
            TourPackage tourDB = tourPackageService.updateTour(tourPackage, byId);
            tourPackageService.save(multipartFile, tourDB);
            return "redirect:/tour";
        }
        return "redirect:/tour";
    }
    @GetMapping("/updateItem")
    public String updateItemPage(ModelMap modelMap) {
        modelMap.addAttribute("regions", regionService.findAll());
        List<Type> types = Arrays.asList(Type.values());
        modelMap.addAttribute("types", types);
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@RequestParam("id") int id,
                             @ModelAttribute Item item,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<Item> byId = itemService.findById(id);
        if (!byId.isEmpty()) {
            Item itemDB = itemService.updateItem(item, byId);
            itemService.addItem(multipartFile, itemDB);
            return "redirect:/item";
        }
        return "redirect:/item";
        }

}