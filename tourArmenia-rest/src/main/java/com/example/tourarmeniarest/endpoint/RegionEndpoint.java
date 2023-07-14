package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.dto.RegionDto;
import com.example.tourarmeniacommon.dto.RegionRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.mapper.RegionMapper;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/regions")
public class RegionEndpoint {
    private final RegionService regionService;
    private final ItemService itemService;
    private final RegionMapper regionMapper;

    @GetMapping
    public ResponseEntity<List<RegionDto>> regionPage() {
        List<Region> all = regionService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<RegionDto> regionDto = regionMapper.mapListToDtos(all);
        return ResponseEntity.ok(regionDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> singleRegionPage(@PathVariable("id") int id,
                                              @RequestBody(required = false) String type) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isPresent()) {
            Region region = byId.get();
            if (type == null) {
                type = "HOTEL";
            }
            return ResponseEntity.ok(itemService.findAllByRegionAndType(region.getId(), Type.valueOf(type)));
        }
        return ResponseEntity.badRequest().build();
    }
}