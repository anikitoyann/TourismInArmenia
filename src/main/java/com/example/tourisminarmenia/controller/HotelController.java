package com.example.tourisminarmenia.controller;

import com.example.tourisminarmenia.entity.Hotel;
import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.respository.HotelRepository;
import com.example.tourisminarmenia.respository.RegionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelRepository hotelRepository;
    private final RegionsRepository regionsRepository;
    @Value("${upload.image.path}")
    private String imageUploadPath;

    @GetMapping
    public String hotelReservetionpage(ModelMap modelMap) {
        modelMap.addAttribute("hotels", hotelRepository.findAll());
        return "hotel";
    }
    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Region> regions=regionsRepository.findAll();
        modelMap.addAttribute("regions",regions);
        return "addHotel";
    }
    @PostMapping("/add")
    public String hotelsAdd(@ModelAttribute Hotel hotel, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            hotel.setPicName(fileName);
        }

        hotelRepository.save(hotel);
        return "redirect:/hotel";
    }
    @GetMapping("/{id}")
    public String singleHotelPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Hotel> byId = hotelRepository.findById(id);
        if (byId.isPresent()) {
            Hotel hotel = byId.get();
            modelMap.addAttribute("hotel", hotel);
            return "singlehotel";
        } else {
            return "redirect:/hotel";
        }

    }
    @GetMapping("/remove")
    public String removeHotels(@RequestParam("id") int id) {
        hotelRepository.deleteById(id);
        return "redirect:/hotel";
    }

}
