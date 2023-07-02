package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
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

    @GetMapping
    public List<Region> regionPage() {
        return regionService.findAll();
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