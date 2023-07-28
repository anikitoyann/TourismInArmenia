package com.example.tourarmeniacommon.service.serviceImpl;
import com.example.tourarmeniacommon.dto.ItemDto;
import com.example.tourarmeniacommon.dto.ItemSearchDto;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Type;
import com.example.tourarmeniacommon.mapper.ItemMapper;
import com.example.tourarmeniacommon.repository.CurrencyRepository;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    private ItemService itemService;

    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @MockBean
    ItemMapper itemMapper;

    @MockBean
    CurrencyRepository currencyRepository;

    @BeforeEach
    public void beforeAll() {
        itemService = new ItemServiceImpl(itemRepository, itemMapper);
    }

    @Test
    void addItem_WithoutFile() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        Item item = new Item();
        item.setName("Test Item");
        item.setDescription("Test description");
        when(multipartFile.isEmpty()).thenReturn(true);
        itemService.addItem(multipartFile, item);
        assertNull(item.getPicName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void findById_WhenItemDoesNotExist() {
        int itemId = 1;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        Optional<Item> result = itemService.findById(itemId);
        assertFalse(result.isPresent());
    }
    @Test
    void findById_WhenItemExists() {
        int itemId = 1;
        Item item = new Item();
        item.setId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Optional<Item> result = itemService.findById(itemId);
        assertTrue(result.isPresent());
        assertEquals(itemId, result.get().getId());
    }

    @Test
    void findAllByType() {
        Type type=Type.HOTEL;
        List<Item> items = new ArrayList<>();
        Pageable pageable = Pageable.unpaged();
        when(itemRepository.findAllByType(type, pageable)).thenReturn(new PageImpl<>(items));
        Page<Item> result = itemService.findAllByType(type, pageable);
        assertEquals(items.size(), result.getContent().size());
    }


    @Test
    void findAllByRegionAndType() {
        Pageable pageable = Pageable.unpaged();
        int regionId = 1;
       Type type=Type.HOTEL;
        List<Item> items = new ArrayList<>();
        // Add some items to the list
        when(itemRepository.findAllByRegion_IdAndType(pageable, regionId, type)).thenReturn(new PageImpl<>(items));
        Page<Item> result = itemService.findAllByRegionAndType(pageable, regionId, type);
        assertEquals(items.size(), result.getContent().size());
    }


    @Test
    void save() {
        Item item = Item.builder()
                .description("asdf")
                .name("asfdsad")
                .build();
        itemService.save(item);
        verify(itemRepository, times(1)).save(item);
    }
    @Test
    void existsById_WhenItemExists() {
        int itemId = 1;
        when(itemRepository.existsById(itemId)).thenReturn(true);
        boolean exists = itemService.existsById(itemId);
        assertTrue(exists);
    }

    @Test
    void existsById_WhenItemDoesNotExist() {
        int itemId = 1;
        when(itemRepository.existsById(itemId)).thenReturn(false);
        boolean exists = itemService.existsById(itemId);
        assertFalse(exists);
    }

//   // @Test
//    void search() {
//        int page = 0;
//        int size = 10;
//        ItemSearchDto itemSearchDto = new ItemSearchDto();
//        // Set properties of the itemSearchDto as needed
//        List<Item> items = new ArrayList<>();
//        when(itemService.search(page, size, itemSearchDto)).thenReturn(new ArrayList<>());
//        when(itemMapper.mapListToDtos(items)).thenReturn(new ArrayList<>());
//        List<ItemDto> result = itemService.search(page, size, itemSearchDto);
//        assertNotNull(result);
//        assertEquals(0, result.size());
//    }

    @Test
    void findAllByPageable() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Item> items = new ArrayList<>();
        when(itemRepository.findAll(pageable)).thenReturn(new PageImpl<>(items));
        Page<Item> result = itemService.findAllByPageable(pageable);
        assertNotNull(result);
        assertEquals(items.size(), result.getContent().size());
    }

    @Test
    void deleteById() {
        int itemId = 1;
        itemService.deleteById(itemId);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void findAll() {
        List<Item> items = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(items);
        List<Item> result = itemService.findAll();
        assertNotNull(result);
        assertEquals(items.size(), result.size());
    }
}