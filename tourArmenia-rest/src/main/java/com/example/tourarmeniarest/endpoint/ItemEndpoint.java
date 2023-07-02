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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemEndpoint {
    private final ItemService itemService;
    private final RegionService regionService;
    private final ItemMapper itemMapper;
    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;

    @GetMapping("/{id}")
    public ResponseEntity<Item> singleItem(@PathVariable("id") int id) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
