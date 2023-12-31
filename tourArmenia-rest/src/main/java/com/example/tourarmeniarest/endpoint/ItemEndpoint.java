package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
@Slf4j
public class ItemEndpoint {
    private final ItemService itemService;

    //Endpoint for retrieving a single item by its ID from the system.
    @GetMapping("/{id}")
    public ResponseEntity<Item> singleItem(@PathVariable("id") int id) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            log.info("Item with id: {} found and returned.", id);
            return ResponseEntity.ok(item);
        }
        log.warn("Item with id: {} not found.", id);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    //Endpoint for searching items based on given criteria in the system.
    @PostMapping("/search")
    public ResponseEntity<List<ItemDto>> getAll(@RequestParam(value = "size", defaultValue = "20") int size,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestBody ItemSearchDto itemSearchDto) {
        log.info("Received request to search items with criteria: {}", itemSearchDto);
        return ResponseEntity.ok(itemService.search(page, size, itemSearchDto));
    }
}
