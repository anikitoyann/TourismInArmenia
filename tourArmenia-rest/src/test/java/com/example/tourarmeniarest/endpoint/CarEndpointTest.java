package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.mapper.CarMapper;
import com.example.tourarmeniacommon.service.CarService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarEndpointTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarEndpoint carEndpoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carEndpoint).build();
    }

    @Test
    void create() {
        // Arrange
        CreateCarRequestDto createCarRequestDto = new CreateCarRequestDto();
        createCarRequestDto.setName("Car 1");
        createCarRequestDto.setSeats("4");
        CarDto carDto = new CarDto();
        carDto.setName(createCarRequestDto.getName());
        carDto.setSeats(createCarRequestDto.getSeats());
        Car car = new Car();
        car.setName(createCarRequestDto.getName());
        car.setSeats(createCarRequestDto.getSeats());
        when(carMapper.map(any(CreateCarRequestDto.class))).thenReturn(car);
        when(carService.create(any(Car.class))).thenReturn(car);
        when(carMapper.mapToDto(any(Car.class))).thenReturn(carDto);
        ResponseEntity<CarDto> response = carEndpoint.create(createCarRequestDto);
        assertEquals(ResponseEntity.ok(carDto).getStatusCodeValue(), response.getStatusCodeValue());
        assertEquals(carDto, response.getBody());
    }

    @Test
    void uploadImage() throws Exception {
        int carId = 1;
        String fileName = "test_image.jpg";
        byte[] imageContent = new byte[]{1, 2, 3};
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image", fileName, MediaType.IMAGE_JPEG_VALUE, imageContent);
        Car car = new Car();
        car.setId(carId);
        car.setPicName(fileName);
        when(carService.findById(carId)).thenReturn(Optional.of(car));
        mockMvc.perform(multipart("/cars/" + carId + "/image")
                        .file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCars() throws Exception {
        CarDto carDto1 = new CarDto();
        carDto1.setName("Car 1");
        carDto1.setSeats("4");

        CarDto carDto2 = new CarDto();
        carDto2.setName("Car 2");
        carDto2.setSeats("7");
        List<CarDto> carDtoList = Arrays.asList(carDto1, carDto2);
        Car car1 = new Car();
        car1.setName("Car 1");
        car1.setSeats("4");

        Car car2 = new Car();
        car2.setName("Car 2");
        car2.setSeats("7");
        List<Car> carList = Arrays.asList(car1, car2);
        when(carService.findAll()).thenReturn(carList);
        when(carMapper.mapListToDtos(carList)).thenReturn(carDtoList);
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(carDto1.getName()))
                .andExpect(jsonPath("$[0].seats").value(carDto1.getSeats()))
                .andExpect(jsonPath("$[1].name").value(carDto2.getName()))
                .andExpect(jsonPath("$[1].seats").value(carDto2.getSeats()));
    }

    @Test
    void deleteById() throws Exception {
        int carIdToDelete = 1;
        when(carService.existsById(carIdToDelete)).thenReturn(true);
        mockMvc.perform(delete("/cars/delete/" + carIdToDelete))
                .andExpect(status().isNoContent());
    }

    @Test
    void update() throws Exception {
        int carIdToUpdate = 1;
        Car carToUpdate = new Car();
        carToUpdate.setName("Updated Car");
        carToUpdate.setSeats("6");

        Car carDb = new Car();
        carDb.setId(carIdToUpdate);
        carDb.setName("Car 1");
        carDb.setSeats("4");

        when(carService.findById(carIdToUpdate)).thenReturn(Optional.of(carDb));
        when(carService.create(any(Car.class))).thenReturn(carDb);
        mockMvc.perform(put("/cars/update/" + carIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Car\",\"seats\":\"6\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(carToUpdate.getName()))
                .andExpect(jsonPath("$.seats").value(carToUpdate.getSeats()));
    }
}
