package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.RegionDto;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.mapper.RegionMapper;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/regions")
@Slf4j
public class RegionEndpoint {
    private final RegionService regionService;
    private final ItemService itemService;
    private final RegionMapper regionMapper;


    // Endpoint for retrieving a page of regions from the system.
    @GetMapping
    public ResponseEntity<List<RegionDto>> regionPage() {
        List<Region> all = regionService.findAll();
        if (all.size() == 0) {
            log.error("there is no any region in DB");
            return ResponseEntity.notFound().build();
        }
        List<RegionDto> regionDto = regionMapper.mapListToDtos(all);
        log.info("Regions retrieved successfully");
        return ResponseEntity.ok(regionDto);
    }

    //Endpoint for retrieving a single region page by its ID and item type from the system.
    @GetMapping("/{id}")
    public ResponseEntity<?> singleRegionPage(@PageableDefault Pageable pageable,
                                              @PathVariable("id") int id,
                                              @RequestBody(required = false) String type) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isPresent()) {
            Region region = byId.get();
            if (type == null) {
                type = "HOTEL";
            }
            return ResponseEntity.ok(itemService.findAllByRegionAndType(pageable,region.getId(), Type.valueOf(type)));
        }
        log.error("Invalid region ID provided: {}", id);
        return ResponseEntity.badRequest().build();
    }
}