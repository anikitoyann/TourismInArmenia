package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.mapper.ItemMapper;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
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
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminEndpoint {
    private final ItemService itemService;
    private final RegionService regionService;
    private final ItemMapper itemMapper;
    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;
    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestBody CreateItemRequestDto createItemRequestDto) {
        Optional<Region> byId = regionService.findById(createItemRequestDto.getRegionId());
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Item save = itemService.save(itemMapper.map(createItemRequestDto));
        return ResponseEntity.ok(itemMapper.mapToDto(save));
    }
    @PostMapping("/{id}/image")
    public ResponseEntity<ItemDto> uploadImage(@PathVariable("id") int itemId,
                                               @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Optional<Item> itemOptional = itemService.findById(itemId);
        if (!multipartFile.isEmpty() && itemOptional.isPresent()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String picName = System.currentTimeMillis() + "_" + originalFilename;
            File file = new File(uploadPath + picName);
            multipartFile.transferTo(file);
            Item item = itemOptional.get();
            item.setPicName(picName);
            itemService.save(item);
            ItemDto itemDto = itemMapper.mapToDto(item);
            return ResponseEntity.ok(itemDto);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") int id) {
        if (itemService.existsById(id)) {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
