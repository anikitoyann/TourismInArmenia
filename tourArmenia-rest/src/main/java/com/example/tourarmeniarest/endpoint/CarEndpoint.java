package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.mapper.CarMapper;
import com.example.tourarmeniacommon.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
@Slf4j
public class CarEndpoint {

    private final CarService carService;
    private final CarMapper carMapper;
    @Value("${upload.image.path}")
    private String uploadPath;

    //Endpoint for creating a new car in the system.
    @PostMapping("/createCar")
    public ResponseEntity<CarDto> create(@RequestBody @Valid CreateCarRequestDto createCarRequestDto) {
        Car car = carService.create(carMapper.map(createCarRequestDto));
        log.info("New car created with ID {}.", car.getId());
        return ResponseEntity.ok(carMapper.mapToDto(car));
    }

    // Endpoint for uploading an image for a specific car.
    @PostMapping("/{id}/image")
    public ResponseEntity<CarDto> uploadImage(@PathVariable("id") int carId,
                                               @RequestParam("image") MultipartFile multipartFile) {
        Optional<Car> carOptional = carService.findById(carId);
        if (!multipartFile.isEmpty() && carOptional.isPresent()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String picName = System.currentTimeMillis() + "_" + originalFilename;
            File file = new File(uploadPath + picName);
            Car car = carOptional.get();
            car.setPicName(picName);
            carService.create(car);
            CarDto carDto = carMapper.mapToDto(car);
            log.info("Image uploaded successfully for car with id: {}", carId);
            return ResponseEntity.ok(carDto);
        }
        log.error("Failed to save the uploaded image for car with id: {}", carId);
        return ResponseEntity.badRequest().build();
    }

   // Endpoint for retrieving a list of all cars in the system.
    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<Car> all = carService.findAll();
        if (all.size() == 0) {
            log.warn("No cars found. Returning 404 - Not Found.");
            return ResponseEntity.notFound().build();
        }
        List<CarDto> carDto = carMapper.mapListToDtos(all);
        log.info("Returning the list of all cars with {} cars.", all.size());
        return ResponseEntity.ok(carDto);
    }

    //Endpoint for deleting a car by its ID from the system.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (carService.existsById(id)) {
            carService.deleteById(id);
            log.info("Car with id: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Car with id: {} not found. Delete request failed.", id);
        return ResponseEntity.notFound().build();
    }

    //Endpoint for updating an existing car in the system.
    @PutMapping("/update/{id}")
    public ResponseEntity<Car> update(@PathVariable("id") int id, @RequestBody Car car) {
        Optional<Car> byId = carService.findById(id);
        if (byId.isEmpty()) {
            log.warn("Car with id: {} not found. Update request failed.", id);
            return ResponseEntity.notFound().build();
        }
        Car carDb = byId.get();
        if (car.getName() != null && !car.getName().isEmpty()) {
            carDb.setName(car.getName());
        }
        if (car.getSeats() != null && !car.getSeats().isEmpty()) {
            carDb.setSeats(car.getSeats());
        }
        carDb.setPicName(car.getPicName());
        log.info("Car with id: {} updated successfully.", id);
        return ResponseEntity.ok(carService.create(carDb));
    }
}
