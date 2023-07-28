package com.example.tourarmeniaweb.controller;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;
    private final UserService userService;
    private final ItemService itemService;

    // Handles the HTTP GET request for "/regions" and returns the "region" view.
    // Adds two attributes to the model: "users" (list of all users) and "regions" (list of all regions).
    @GetMapping
    public String regionPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        modelMap.addAttribute("regions", regionService.findAll());
        return "region";
    }


    // Handles the HTTP GET request with "/{id}" path variable and returns the "singleRegion" view.
    // It takes additional request parameters: "pageable" (for pagination), "type" (optional, defaults to "HOTEL"),
    // and "modelMap" (to add attributes to the model for rendering).
    @GetMapping("/{id}")
    public String singleRegionPage(@PathVariable("id") int id,
                                   @PageableDefault Pageable pageable,
                                   @RequestParam(required = false) String type,
                                   ModelMap modelMap) {
        log.info("Received request for singleRegionPage with id: {}", id);
        Optional<Region> byId = regionService.findById(id);
        if (byId.isPresent()) {
            Region region = byId.get();
            log.info("Region with id {} found: {}", id, region);
            if (type == null) {
                type = "HOTEL";
                log.info("Type parameter not provided. Using default value 'HOTEL'.");
            }
            log.info("Request for region with id: {} and type: {}", id, type);
            Page<Item> result = itemService.findAllByRegionAndType(pageable, region.getId(), Type.valueOf(type));
            modelMap.addAttribute("region", region);
            modelMap.addAttribute("items", result);
            return "singleRegion";
        } else {
            log.warn("Region with id: {} not found. Redirecting to /regions.", id);
            return "redirect:/regions";
        }
    }
    }

