package com.example.tourarmeniarest.endpoint;
import com.example.tourarmeniacommon.dto.CreateItemRequestDto;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.mapper.ItemMapper;
import com.example.tourarmeniacommon.mapper.RegionMapper;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniacommon.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminEndpointTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @Mock
    private RegionService regionService;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private RegionMapper regionMapper;

    @InjectMocks
    private AdminEndpoint adminEndpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminEndpoint).build();
    }

//    @Test
//    void create() {
//        CreateItemRequestDto createItemRequestDto = new CreateItemRequestDto();
//        createItemRequestDto.setRegionId(1);
//        ItemDto itemDto = new ItemDto();
//        Item item = new Item();
//        Region region = new Region();
//        region.setId(createItemRequestDto.getRegionId());
//        when(regionService.findById(createItemRequestDto.getRegionId())).thenReturn(Optional.of(region));
//        when(itemMapper.map(any(CreateItemRequestDto.class))).thenReturn(item);
//        when(itemService.save(item)).thenReturn(item);
//        when(itemMapper.mapToDto(item)).thenReturn(itemDto);
//        ResponseEntity<ItemDto> response = adminEndpoint.create(createItemRequestDto);
//        assertEquals(ResponseEntity.ok(itemDto).getStatusCodeValue(), response.getStatusCodeValue());
//        assertEquals(itemDto, response.getBody());
//    }
//
//
//    @Test
//    void uploadImage() throws Exception {
//        int itemId = 1;
//        String fileName = "test_image.jpg";
//        byte[] imageContent = new byte[]{1, 2, 3};
//        MockMultipartFile multipartFile = new MockMultipartFile(
//                "image", fileName, MediaType.IMAGE_JPEG_VALUE, imageContent);
//        Item item = new Item();
//        item.setId(itemId);
//        item.setPicName(fileName);
//        when(itemService.findById(itemId)).thenReturn(Optional.of(item));
//
//        mockMvc.perform(multipart("/admin/" + itemId + "/image")
//                        .file(multipartFile))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void update() throws Exception {
//        int itemIdToUpdate = 1;
//        Item itemToUpdate = new Item();
//        itemToUpdate.setName("Updated Item");
//        Item itemFromDB = new Item();
//        itemFromDB.setId(itemIdToUpdate);
//        itemFromDB.setName("Item 1");
//        when(itemService.findById(itemIdToUpdate)).thenReturn(Optional.of(itemFromDB));
//        when(itemService.save(any(Item.class))).thenReturn(itemFromDB);
//        mockMvc.perform(put("/admin/updateItem/" + itemIdToUpdate)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"Updated Item\",\"description\":\"Updated description\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(itemToUpdate.getName()));
//    }
//
//
//    @Test
//    void deleteItemById() throws Exception {
//        int itemIdToDelete = 1;
//        when(itemService.existsById(itemIdToDelete)).thenReturn(true);
//        mockMvc.perform(delete("/admin/delete/" + itemIdToDelete))
//                .andExpect(status().isNoContent());
//    }
}
