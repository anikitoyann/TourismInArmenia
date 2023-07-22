package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemEndpoint {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<Item> singleItem(@PathVariable("id") int id) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @PostMapping("/search")
    public ResponseEntity<List<ItemDto>> getAll(@RequestParam(value = "size", defaultValue = "20") int size,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestBody ItemSearchDto itemSearchDto) {
        return ResponseEntity.ok(itemService.search(page, size, itemSearchDto));
    }
}
