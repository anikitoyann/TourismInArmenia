package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final RegionService regionService;

    // Handler for HTTP GET requests with optional query parameters "page" and "size"
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
        log.info("Handling hotelReservationPage request");
        return "item";

    }


    @GetMapping("/{id}")
    public String singleHotelPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            modelMap.addAttribute("item", item);
            log.info("Handling singleHotelPage request for item with ID: {}", id);
            return "singlehotel";
        } else {
            log.warn("Requested item with ID: {} not found. Redirecting to /item", id);
            return "redirect:/item";
        }
    }

    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {
        List<Item> items;
        String message;
        if (regionId != null) {
            Region region = regionService.findById(regionId).orElse(null);
            log.info("Fetching hotels for regionId: {}", regionId);
            if (region != null) {
                items = itemService.findByRegion(region);
                log.info("Region found: {}", region.getName());
                if (items.isEmpty()) {
                    message = "No hotels found in the selected region.";
                } else {
                    log.warn("Invalid regionId: {}", regionId);
                    message = "Hotels in the selected region:";
                }
            } else {
                items = itemService.findAll();
                log.info("Hotels found in the selected region: {}", items.size());
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
