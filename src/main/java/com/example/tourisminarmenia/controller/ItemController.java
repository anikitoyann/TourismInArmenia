package com.example.tourisminarmenia.controller;
import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.Type;
import com.example.tourisminarmenia.respository.ItemRepository;
import com.example.tourisminarmenia.respository.RegionRepository;
import com.example.tourisminarmenia.service.ItemService;
import com.example.tourisminarmenia.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemRepository itemRepository;
    private final RegionRepository regionsRepository;
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

        Page<Item> result = itemRepository.findAllByType(Type.HOTEL,pageable);
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("items", result);
        return "hotel";

    }
    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        modelMap.addAttribute("regions", regionService.findAll());
        List<Type> types = Arrays.asList(Type.values());
        modelMap.addAttribute("types", types);
        return "addItem";
    }
    @PostMapping("/add")
    public String hotelsAdd(@ModelAttribute Item item, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        itemService.addItem(multipartFile, item);
        return "redirect:/item";
        // return "redirect:/regions/" + item.getRegion().getId();
    }

    @GetMapping("/{id}")
    public String singleHotelPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            modelMap.addAttribute("item", item);
            return "singlehotel";
        } else {
            return "redirect:/item";
        }

    }
    @GetMapping("/remove")
    public String removeHotels(@RequestParam("id") int id) {
        itemRepository.deleteById(id);
        return "redirect:/item";
    }
    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {
        List<Item> items;
        String message;

        if (regionId != null) {
            Region region = regionsRepository.findById(regionId).orElse(null);

            if (region != null) {
                items = itemRepository.findByRegion(region);

                if (items.isEmpty()) {
                    message = "No hotels found in the selected region.";
                } else {
                    message = "Hotels in the selected region:";
                }
            } else {
                items = itemRepository.findAll();
                message = "Invalid region selected. Showing all hotels instead.";
            }
        } else {
            items = itemRepository.findAll();
            message = "Showing all hotels:";
        }
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("regions", regionsRepository.findAll());
        modelMap.addAttribute("message", message);

        return "hotel";
    }
}
