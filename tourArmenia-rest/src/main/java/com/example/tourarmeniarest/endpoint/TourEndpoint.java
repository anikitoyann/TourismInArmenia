package com.example.tourarmeniarest.endpoint;
import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.mapper.TourMapper;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniacommon.util.RoundUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tours")
public class TourEndpoint {
    private final RegionService regionService;
    private final ItemService itemService;
    private final CarService carService;
    private final TourPackageService tourPackageService;
    private final TourMapper tourMapper;
    private final CurrencyService currencyService;
    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;
    @PostMapping("/createTour")
    public ResponseEntity<TourDto> create(@RequestBody CreateTourRequestDto createTourRequestDto) {
        Optional<Region> regionId = regionService.findById(createTourRequestDto.getRegionId());
        Optional<Item> itemId = itemService.findById(createTourRequestDto.getItemId());
        Optional<Car> carId = carService.findById(createTourRequestDto.getCarId());
        if(regionId.isEmpty() && carId.isEmpty() && itemId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TourPackage saved = tourPackageService.add(tourMapper.map(createTourRequestDto));
        saved.setRegion(regionId.get());
        saved.setCar(carId.get());
        saved.setItem(itemId.get());
        return ResponseEntity.ok(tourMapper.mapToDto(saved));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (tourPackageService.existsById(id)) {
            tourPackageService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{id}/image")
    public ResponseEntity<TourDto> uploadImage(@PathVariable("id") int tourId,
                                               @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<TourPackage> tourOptional = tourPackageService.findById(tourId);
        if (!multipartFile.isEmpty() && tourOptional.isPresent()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String picName = System.currentTimeMillis() + "_" + originalFilename;
            File file = new File(uploadPath + picName);
            multipartFile.transferTo(file);
            TourPackage tour = tourOptional.get();
            tour.setPicName(picName);
            tourPackageService.add(tour);
            TourDto tourDto = tourMapper.mapToDto(tour);
            return ResponseEntity.ok(tourDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAll() {
        List<TourPackage> all = tourPackageService.findAll();
        List<TourDto> tourDtoList = tourMapper.mapListToDtos(all);
        List<Currency> currencies = currencyService.findAll();
        if (!currencies.isEmpty()) {
            Currency currency = currencies.get(0);
            for (TourDto tourDto : tourDtoList) {
                double priceAmd = tourDto.getPriceAmd();
                tourDto.setPriceRub(RoundUtil.round(priceAmd / currency.getRub(), 2));
                tourDto.setPriceUsd(RoundUtil.round(priceAmd / currency.getUsd(), 1));
            }
        }
        return ResponseEntity.ok(tourDtoList);
    }
}