package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @GetMapping
    public String regionPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        modelMap.addAttribute("regions", regionService.findAll());
        return "region";
    }

    @GetMapping("/{id}")
    public String singleRegionPage(@PathVariable("id") int id,
                                   @RequestParam(required = false) String type,
                                   ModelMap modelMap) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isPresent()) {
            Region region = byId.get();
            if (type == null) {
                type = "HOTEL";
            }
            List<Item> items = itemRepository.findAllByRegion_IdAndType(region.getId(), Type.valueOf(type));
            modelMap.addAttribute("region", region);
            modelMap.addAttribute("items", items);
            return "singleRegion";
        } else {
            return "redirect:/regions";
        }
    }
}
