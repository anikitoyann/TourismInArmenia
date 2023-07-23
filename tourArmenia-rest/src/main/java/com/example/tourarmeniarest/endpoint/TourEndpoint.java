package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.mapper.TourMapper;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniacommon.util.RoundUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    @PutMapping("/update/{id}")
    public ResponseEntity<TourPackage> update(@PathVariable("id") int id, @RequestBody TourPackage tourPackage) {
        Optional<TourPackage> byId = tourPackageService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        TourPackage tourPackageDB = byId.get();
        updateTour(tourPackage, tourPackageDB);
        return ResponseEntity.ok(tourPackageService.add(tourPackageDB));
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
    private static void updateTour(TourPackage tourPackage, TourPackage tourPackageDB) {
        if (tourPackage.getName() != null && !tourPackage.getName().isEmpty()) {
            tourPackageDB.setName(tourPackage.getName());
        }
        if (tourPackage.getDuration() != null && tourPackage.getDuration().isEmpty()) {
            tourPackageDB.setDuration(tourPackage.getDuration());
        }
        if (tourPackage.getStartDate() != null) {
            tourPackageDB.setStartDate(tourPackage.getStartDate());
        }
        if (tourPackage.getCar() != null) {
            tourPackageDB.setCar(tourPackage.getCar());
        }
        if (tourPackage.getRegion() != null) {
            tourPackageDB.setRegion(tourPackage.getRegion());
        }
        if (tourPackage.getItem() != null) {
            tourPackageDB.setItem(tourPackage.getItem());
        }
        if (tourPackage.getPrice() != 0) {
            tourPackageDB.setPrice(tourPackage.getPrice());
        }
        if (tourPackage.getGroupSize() != 0) {
            tourPackageDB.setGroupSize(tourPackage.getGroupSize());
        }
        tourPackageDB.setPicName(tourPackage.getPicName());
    }
}