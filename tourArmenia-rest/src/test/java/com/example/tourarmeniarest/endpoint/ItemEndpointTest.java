package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ItemEndpointTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemEndpoint itemEndpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemEndpoint).build();
    }

    @Test
    void singleItem() {
        int itemId = 1;
        Item item = new Item();
        item.setId(itemId);
        item.setName("Test Item");
        when(itemService.findById(itemId)).thenReturn(Optional.of(item));
        ResponseEntity<Item> response = itemEndpoint.singleItem(itemId);
        assertEquals(ResponseEntity.ok(item).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(item, response.getBody());
    }

    @Test
    void getAll() {
        int size = 20;
        int page = 0;
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        List<ItemDto> itemDtos = new ArrayList<>();
        when(itemService.search(page, size, itemSearchDto)).thenReturn(itemDtos);
        ResponseEntity<List<ItemDto>> response = itemEndpoint.getAll(size, page, itemSearchDto);
        assertEquals(ResponseEntity.ok(itemDtos).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(itemDtos, response.getBody());
    }
}
