package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    private final CommentService commentService;

    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String toursPage(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourService.findAll());
        modelMap.addAttribute("regions", regionService.findAll());

        return "tour";
    }

    @GetMapping("/add")
    public String toursAddPage(ModelMap modelMap) {
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
        tourService.save(tourPackages, multipartFile);
        return "redirect:/tour";
    }

    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {

        String msg;
        List<TourPackage> tourPackages;

        if (regionId != null) {
            Region region = regionService.findById(regionId).orElse(null);

            if (region != null) {
                tourPackages = tourService.findByRegion(region);
                if (tourPackages.isEmpty()) {
                    msg = "At present, we do not have any scheduled tours in this region.";
                } else {
                    msg = null;
                }
            } else {
                tourPackages = tourService.findAll();
                msg = "invalid region";
            }
        } else {
            msg = null;
            tourPackages = tourService.findAll();
        }

        modelMap.addAttribute("tourPackages", tourPackages);
        modelMap.addAttribute("regions", regionService.findAll());
        modelMap.addAttribute("msg", msg);
        return "tour";
    }

    @GetMapping("/{id}")
    public String singleTourPage(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap) {
        Optional<TourPackage> byId = tourService.findById(id);
        if (byId.isPresent()) {
            TourPackage tourPackage = byId.get();
            User user = currentUser.getUser();
            List<Comment> comments = commentService.findAllByTourId(id);
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
        commentService.save(comment);

        return "redirect:/tour/" + comment.getTour().getId();
    }

    @GetMapping("/remove")
    public String removeTour(@RequestParam("id") int id) {
        tourService.deleteById(id);
        return "redirect:/tour";
    }
}

