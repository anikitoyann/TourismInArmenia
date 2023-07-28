package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.mapper.TourMapper;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniacommon.util.RoundUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tours")
@Slf4j
public class TourEndpoint {
    private final RegionService regionService;
    private final ItemService itemService;
    private final CarService carService;
    private final TourPackageService tourPackageService;
    private final TourMapper tourMapper;
    private final CurrencyService currencyService;
    @Value("${upload.image.path}")
    private String uploadPath;

    //Endpoint for creating a new tour in the system.
    @PostMapping("/createTour")
    public ResponseEntity<TourDto> create(@RequestBody @Valid CreateTourRequestDto createTourRequestDto) {
        Optional<Region> regionId = regionService.findById(createTourRequestDto.getRegionId());
        Optional<Item> itemId = itemService.findById(createTourRequestDto.getItemId());
        Optional<Car> carId = carService.findById(createTourRequestDto.getCarId());
        if (regionId.isEmpty() && carId.isEmpty() && itemId.isEmpty()) {
            log.warn("Invalid data provided for creating a tour. Region, Item, or Car not found.");
            return ResponseEntity.badRequest().build();
        }
        TourPackage saved = tourPackageService.add(tourMapper.map(createTourRequestDto));
        saved.setRegion(regionId.get());
        saved.setCar(carId.get());
        saved.setItem(itemId.get());
        log.info("Tour created successfully");
        return ResponseEntity.ok(tourMapper.mapToDto(saved));
    }

    //Endpoint for deleting a tour by its ID from the system.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (tourPackageService.existsById(id)) {
            tourPackageService.deleteById(id);
            log.info("Tour with id: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        }
        log.info("Tour with id: {} deleted successfully.", id);
        return ResponseEntity.notFound().build();
    }

    //Endpoint for updating an existing tour in the system by its ID.
    @PutMapping("/update/{id}")
    public ResponseEntity<TourPackage> update(@PathVariable("id") int id, @RequestBody TourPackage tourPackage) {
        Optional<TourPackage> byId = tourPackageService.findById(id);
        if (byId.isEmpty()) {
            log.warn("Tour with id: {} not found. Update request failed.", id);
            return ResponseEntity.notFound().build();
        }
        TourPackage tourPackageDB = byId.get();
        updateTour(tourPackage, tourPackageDB);
        log.info("Tour with id: {} updated successfully.", id);
        return ResponseEntity.ok(tourPackageService.add(tourPackageDB));
    }

    //Endpoint for uploading an image for a tour by its ID in the system.
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
            log.info("Image uploaded successfully for tour with id: {}", tourId);
            return ResponseEntity.ok(tourDto);
        }
        log.warn("Image upload failed for tour with id: {}. Either the tour does not exist or no image file provided.", tourId);
        return ResponseEntity.badRequest().build();
    }

    //Endpoint for retrieving a list of all tours from the system.
    // Converts the tour prices from AMD (Armenian Dram) to RUB (Russian Ruble) and USD (United States Dollar)
    // using the currency exchange rates obtained from the currencyService.
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

    //Helper method to update an existing TourPackage entity with the values from another TourPackage entity.
    // The method updates specific fields of the existing TourPackage (tourPackageDB) based on the non-null fields of the provided TourPackage (tourPackage).
    // The picName field of the existing TourPackage is also updated with the picName from the provided TourPackage.
    // After updating the fields, the updated TourPackage is saved in the database using tourPackageService.add() method.
    public ResponseEntity<TourPackage> updateTour(TourPackage tourPackage, TourPackage tourPackageDB) {
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
        log.info("TourPackage updated successfully with new values.");
        return ResponseEntity.ok(tourPackageService.add(tourPackageDB));
    }
}