package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.repository.*;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tour")
public class TourPackagesController {
    private final TourPackagesRepository tourPackagesRepository;
    private final RegionRepository regionsRepository;
    private final CarRepository carsRepository;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;


    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String toursPage(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourPackagesRepository.findAll());
        modelMap.addAttribute("regions", regionsRepository.findAll());

        return "tour";
    }

    @GetMapping("/add")
    public String toursAddPage(ModelMap modelMap) {
        List<Region> regions = regionsRepository.findAll();
        List<Car> cars = carsRepository.findAll();
        List<Item> items = itemRepository.findAll();
        modelMap.addAttribute("regions", regions);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        return "addTours";
    }

    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {

        String msg;
        List<TourPackage> tourPackages;

        if (regionId != null) {
            Region region = regionsRepository.findById(regionId).orElse(null);

            if (region != null) {
                tourPackages = tourPackagesRepository.findByRegion(region);
                if (tourPackages.isEmpty()) {
                    msg = "At present, we do not have any scheduled tours in this region.";
                } else {
                    msg = null;
                }
            } else {
                tourPackages=tourPackagesRepository.findAll();
                msg = "invalid region";
            }
        } else {
            msg=null;
           tourPackages = tourPackagesRepository.findAll();
        }

        modelMap.addAttribute("tourPackages", tourPackages);
        modelMap.addAttribute("regions", regionsRepository.findAll());
        modelMap.addAttribute("msg", msg);
        return "tour";
    }


    @PostMapping("/add")
    public String tourPackagesAdd(@ModelAttribute TourPackage tourPackages, @RequestParam("image") MultipartFile
            multipartFile) throws IOException {
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
    public String singleTourPage(@PathVariable("id") int id,@AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap){
        Optional<TourPackage> byId = tourPackagesRepository.findById(id);
        if (byId.isPresent()) {
            TourPackage tourPackage = byId.get();
            User user = currentUser.getUser();
            List<Comment> comments = commentRepository.findAllByTourId(id);
            modelMap.addAttribute("tour", tourPackage);
            modelMap.addAttribute("comments", comments);
            modelMap.addAttribute("user", user);

            return "singleTour";
        } else {
            return "redirect:/tour";
        }

    }
    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        comment.setUser(user);
        comment.setDate(new Date());
        commentRepository.save(comment);

        return "redirect:/tour/" + comment.getTour().getId();
    }

@GetMapping("/remove")
    public String removeTour(@RequestParam("id") int id){
    tourPackagesRepository.deleteById(id);
    return "redirect:/tour";
    }}

