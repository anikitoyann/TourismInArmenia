package com.example.tourisminarmenia.controller;
import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.Type;
import com.example.tourisminarmenia.respository.ItemRepository;
import com.example.tourisminarmenia.respository.RegionsRepository;
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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class HotelController {
    private final ItemRepository itemRepository;
    private final RegionsRepository regionsRepository;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String hotelReservetionpage(@RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size,ModelMap modelMap) {
        modelMap.addAttribute("hotels", itemRepository.findAll());
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Item> result = itemRepository.findAll(pageable);
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
        List<Region> regions=regionsRepository.findAll();
        modelMap.addAttribute("regions",regions);
        List<Type> types = Arrays.asList(Type.values());
        modelMap.addAttribute("types", types);
        return "addHotel";
    }
    @PostMapping("/add")
    public String hotelsAdd(@ModelAttribute Item item, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            item.setPicName(fileName);
        }

        itemRepository.save(item);
        return "redirect:/item";
    }
    @GetMapping("/{id}")
    public String singleHotelPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemRepository.findById(id);
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
