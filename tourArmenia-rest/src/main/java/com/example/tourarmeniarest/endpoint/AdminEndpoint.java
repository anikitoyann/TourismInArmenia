package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.RegionDto;
import com.example.tourarmeniacommon.dto.RegionRequestDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.mapper.ItemMapper;
import com.example.tourarmeniacommon.mapper.RegionMapper;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminEndpoint {
    private final ItemService itemService;
    private final RegionService regionService;
    private final ItemMapper itemMapper;
    private final RegionMapper regionMapper;
    @Value("${upload.image.path}")
    private String uploadPath;

    @PostMapping("/createItem")
    public ResponseEntity<ItemDto> create(@RequestBody CreateItemRequestDto createItemRequestDto) {
        Optional<Region> byId = regionService.findById(createItemRequestDto.getRegionId());
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Item save = itemService.save(itemMapper.map(createItemRequestDto));
        save.setRegion(byId.get());
        return ResponseEntity.ok(itemMapper.mapToDto(save));
    }


    @PostMapping("/createRegion")

    public ResponseEntity<RegionDto> create(@RequestBody RegionRequestDto regionRequestDto) {
        Region region = regionService.save(regionMapper.map(regionRequestDto));
        return ResponseEntity.ok(regionMapper.mapToDto(region));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<ItemDto> uploadImage(@PathVariable("id") int itemId,
                                               @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<Item> itemOptional = itemService.findById(itemId);
        if (!multipartFile.isEmpty() && itemOptional.isPresent()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String picName = System.currentTimeMillis() + "_" + originalFilename;
            File file = new File(uploadPath + picName);
            Item item = itemOptional.get();
            item.setPicName(picName);
            itemService.save(item);
            ItemDto itemDto = itemMapper.mapToDto(item);
            return ResponseEntity.ok(itemDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<Item> update(@PathVariable("id") int id, @RequestBody Item item) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Item itemFromDB = byId.get();
        if (item.getName() != null && !item.getName().isEmpty()) {
            itemFromDB.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            itemFromDB.setDescription(item.getDescription());
        }
        return ResponseEntity.ok(itemService.save(itemFromDB));
    }

    @PutMapping("/updateRegion/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable("id") int id, @RequestBody Region region) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Region regionFromDB = byId.get();
        if (region.getName() != null && !region.getName().isEmpty()) {
            regionFromDB.setName(region.getName());
        }
        if (region.getRegionalCenter() != null && !region.getRegionalCenter().isEmpty()) {
            regionFromDB.setRegionalCenter(region.getRegionalCenter());
        }
        return ResponseEntity.ok(regionService.save(regionFromDB));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") int id) {
        if (itemService.existsById(id)) {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
