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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminEndpoint {
    private final ItemService itemService;
    private final RegionService regionService;
    private final ItemMapper itemMapper;
    private final RegionMapper regionMapper;
    @Value("${upload.image.path}")
    private String uploadPath;


    //Endpoint for creating a new item in the system.
    @PostMapping("/createItem")
    public ResponseEntity<ItemDto> create(@RequestBody CreateItemRequestDto createItemRequestDto) {
        Optional<Region> byId = regionService.findById(createItemRequestDto.getRegionId());
        if (byId.isEmpty()) {
            log.warn("Region with ID {} not found. Failed to create item.", createItemRequestDto.getRegionId());
            return ResponseEntity.badRequest().build();
        }
        // Save the new item to the database using the itemService and set its region to the retrieved Region entity.
        Item save = itemService.save(itemMapper.map(createItemRequestDto));
        save.setRegion(byId.get());
        log.info("New item created with ID {}.", save.getId());
        return ResponseEntity.ok(itemMapper.mapToDto(save));
    }

    //Endpoint for creating a new region in the system.
    @PostMapping("/createRegion")

    public ResponseEntity<RegionDto> create(@RequestBody RegionRequestDto regionRequestDto) {
        Region region = regionService.save(regionMapper.map(regionRequestDto));
        log.info("New region created with ID {}.", region.getId());
        return ResponseEntity.ok(regionMapper.mapToDto(region));
    }

    /**
     * Endpoint for uploading an image for a specific item.
     *
     * @param itemId       The ID of the item to which the image will be associated.
     * @param multipartFile The image file to be uploaded as a MultiPart file.
     * @return ResponseEntity<ItemDto> containing the updated item information with the uploaded image if successful,
     *         or a bad request response if the item does not exist or no image file was provided.
     * @throws IOException If there is an issue while processing the image file.
     */
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
            log.info("Image uploaded successfully for item with id: {}", itemId);
            return ResponseEntity.ok(itemDto);
        }
        log.warn("Image upload failed for item with id: {}. Either the item does not exist or no image file provided.", itemId);
        return ResponseEntity.badRequest().build();
    }

    //Endpoint for updating an existing item in the system.
    @PutMapping("/updateItem/{id}")
    public ResponseEntity<Item> update(@PathVariable("id") int id, @RequestBody Item item) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isEmpty()) {
            log.warn("Item with id: {} not found. Update request failed.", id);
            return ResponseEntity.notFound().build();
        }
        Item itemFromDB = byId.get();
        if (item.getName() != null && !item.getName().isEmpty()) {
            itemFromDB.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            itemFromDB.setDescription(item.getDescription());
        }
        log.info("Item with id: {} updated successfully.", id);
        return ResponseEntity.ok(itemService.save(itemFromDB));
    }

    // Endpoint for updating an existing region in the system.
    @PutMapping("/updateRegion/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable("id") int id, @RequestBody Region region) {
        Optional<Region> byId = regionService.findById(id);
        if (byId.isEmpty()) {
            log.warn("Region with id: {} not found. Update request failed.", id);
            return ResponseEntity.notFound().build();
        }
        Region regionFromDB = byId.get();
        if (region.getName() != null && !region.getName().isEmpty()) {
            regionFromDB.setName(region.getName());
        }
        if (region.getRegionalCenter() != null && !region.getRegionalCenter().isEmpty()) {
            regionFromDB.setRegionalCenter(region.getRegionalCenter());
        }
        log.info("Region with id: {} updated successfully.", id);
        return ResponseEntity.ok(regionService.save(regionFromDB));
    }

   // Endpoint for deleting an item by its ID from the system.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") int id) {
        if (itemService.existsById(id)) {
            itemService.deleteById(id);
            log.info("Item with id: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Item with id: {} not found. Delete request failed.", id);
        return ResponseEntity.notFound().build();
    }
}
