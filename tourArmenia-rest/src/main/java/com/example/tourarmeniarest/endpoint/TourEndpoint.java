package com.example.tourarmeniarest.endpoint;
import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.mapper.TourMapper;
import com.example.tourarmeniacommon.service.RegionService;
import com.example.tourarmeniacommon.service.TourPackageService;
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
    private final TourPackageService tourPackageService;
    private final TourMapper tourMapper;
    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;
    @PostMapping
    public ResponseEntity<TourDto> create(@RequestBody CreateTourRequestDto createTourRequestDto) {
        Optional<Region> byId = regionService.findById(createTourRequestDto.getRegionId());
        if(byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TourPackage saved = tourPackageService.add(tourMapper.map(createTourRequestDto));
        saved.setRegion(byId.get());
        return ResponseEntity.ok(tourMapper.mapToDto(saved));
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

    @GetMapping(value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        File file = new File(uploadPath + picName);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            return IOUtils.toByteArray(fileInputStream);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAll() {
        List<TourPackage> all = tourPackageService.findAll();
        List<TourDto> tourDtoList = tourMapper.mapListToDtos(all);
        return ResponseEntity.ok(tourDtoList);
    }
}