package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final RegionService regionService;

    @GetMapping
    public String hotelReservationPage(
            @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size,ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Item> result = itemService.findAllByType(Type.HOTEL,pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("items", result);
        return "item";

    }


    @GetMapping("/{id}")
    public String singleHotelPage(@PathVariable("id") int id,
                                  @AuthenticationPrincipal CurrentUser currentUser,
                                  ModelMap modelMap) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            User user = currentUser.getUser();
            modelMap.addAttribute("item", item);
            modelMap.addAttribute("user",user);
            return "singlehotel";
        } else {
            return "redirect:/item";
        }

    }

    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {
        List<Item> items;
        String message;
        if (regionId != null) {
            Region region = regionService.findById(regionId).orElse(null);
            if (region != null) {
                items = itemService.findByRegion(region);
                if (items.isEmpty()) {
                    message = "No hotels found in the selected region.";
                } else {
                    message = "Hotels in the selected region:";
                }
            } else {
                items = itemService.findAll();
                message = "Invalid region selected. Showing all hotels instead.";
            }
        } else {
            items = itemService.findAll();
            message = "Showing all hotels:";
        }
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("regions", regionService.findAll());
        modelMap.addAttribute("message", message);

        return "item";
    }
}
