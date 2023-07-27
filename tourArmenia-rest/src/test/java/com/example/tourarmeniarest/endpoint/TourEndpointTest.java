package com.example.tourarmeniarest.endpoint;
import com.example.tourarmeniacommon.dto.CreateTourRequestDto;
import com.example.tourarmeniacommon.dto.TourDto;
import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.mapper.TourMapper;
import com.example.tourarmeniacommon.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class TourEndpointTest {

    @Mock
    private RegionService regionService;
    @Mock
    private ItemService itemService;

    @Mock
    private CarService carService;

    @Mock
    private TourPackageService tourPackageService;

    @Mock
    private TourMapper tourMapper;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private TourEndpoint tourEndpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        int regionId = 1;
        int itemId = 2;
        int carId = 3;
        CreateTourRequestDto createTourRequestDto = new CreateTourRequestDto();
        createTourRequestDto.setRegionId(regionId);
        createTourRequestDto.setItemId(itemId);
        createTourRequestDto.setCarId(carId);
        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(1);
        when(regionService.findById(regionId)).thenReturn(Optional.of(new Region()));
        when(itemService.findById(itemId)).thenReturn(Optional.of(new Item()));
        when(carService.findById(carId)).thenReturn(Optional.of(new Car()));
        when(tourMapper.map(createTourRequestDto)).thenReturn(tourPackage);
        when(tourPackageService.add(tourPackage)).thenReturn(tourPackage);
        ResponseEntity<TourDto> response = tourEndpoint.create(createTourRequestDto);
        assertEquals(ResponseEntity.ok(tourMapper.mapToDto(tourPackage)).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(tourPackage, tourMapper.map(createTourRequestDto));
    }
    @Test
    void deleteById() {
        int tourId = 1;

        when(tourPackageService.existsById(tourId)).thenReturn(true);
        ResponseEntity<?> response = tourEndpoint.deleteById(tourId);
        assertEquals(ResponseEntity.noContent().build().getStatusCodeValue(), response.getStatusCodeValue());
        verify(tourPackageService, times(1)).deleteById(tourId);
    }

    @Test
    void update() {
        int tourId = 1;

        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(tourId);
        TourPackage tourPackageDB = new TourPackage();
        tourPackageDB.setId(tourId);
        when(tourPackageService.findById(tourId)).thenReturn(Optional.of(tourPackage));
        when(tourPackageService.add(tourPackageDB)).thenReturn(tourPackageDB);
        ResponseEntity<TourPackage> response = tourEndpoint.update(tourId, tourPackage);
        assertEquals(ResponseEntity.ok(tourPackageDB).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(tourPackageDB, response.getBody());
    }

    @Test
    void uploadImage() throws IOException {
        int tourId = 1;

        TourPackage tourPackage = new TourPackage();
        tourPackage.setId(tourId);
        Optional<TourPackage> tourOptional = Optional.of(tourPackage);
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(tourPackageService.findById(tourId)).thenReturn(tourOptional);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test_image.jpg");
        ResponseEntity<TourDto> response = tourEndpoint.uploadImage(tourId, multipartFile);
        assertEquals(ResponseEntity.ok(tourMapper.mapToDto(tourPackage)).getStatusCodeValue(), response.getStatusCodeValue());
        verify(tourPackageService, times(1)).add(tourPackage);
    }

    @Test
    void getAll() {
        List<TourPackage> tourPackages = mock(List.class);
        List<TourDto> tourDtoList = mock(List.class);
        Currency currency = new Currency();
        when(tourPackageService.findAll()).thenReturn(tourPackages);
        when(currencyService.findAll()).thenReturn(List.of(currency));
        when(tourMapper.mapListToDtos(tourPackages)).thenReturn(tourDtoList);
        ResponseEntity<List<TourDto>> response = tourEndpoint.getAll();
        assertEquals(ResponseEntity.ok(tourDtoList).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(tourDtoList, response.getBody());
    }}