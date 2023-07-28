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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    void getAll() {
        // Arrange
        int size = 20;
        int page = 0;
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        // Set criteria in itemSearchDto...

        List<ItemDto> itemDtos = new ArrayList<>();
        // Add items to itemDtos...

        when(itemService.search(page, size, itemSearchDto)).thenReturn(itemDtos);

        // Act
        ResponseEntity<List<ItemDto>> response = itemEndpoint.getAll(size, page, itemSearchDto);

        // Assert
        assertEquals(ResponseEntity.ok(itemDtos).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(itemDtos, response.getBody());
    }
}
