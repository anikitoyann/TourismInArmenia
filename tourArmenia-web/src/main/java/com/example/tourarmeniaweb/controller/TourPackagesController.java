package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tour")
public class TourPackagesController {
    private final TourPackagesRepository tourPackagesRepository;

    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String toursPage(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourPackagesRepository.findAll());
        return "tour";
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

