package com.example.tourarmeniarest.endpoint;

import com.example.tourarmeniacommon.dto.CarDto;
import com.example.tourarmeniacommon.dto.CreateCarRequestDto;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.mapper.CarMapper;
import com.example.tourarmeniacommon.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cars")
public class CarEndpoint {

    private final CarService carService;
    private final CarMapper carMapper;
    @Value("${upload.image.path}")
    private String uploadPath;
    @PostMapping("/createCar")
    public ResponseEntity<CarDto> create(@RequestBody CreateCarRequestDto createCarRequestDto) {
        Car car = carService.create(carMapper.map(createCarRequestDto));
        return ResponseEntity.ok(carMapper.mapToDto(car));
    }

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
            return ResponseEntity.ok(carDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<Car> all = carService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<CarDto> carDto = carMapper.mapListToDtos(all);
        return ResponseEntity.ok(carDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (carService.existsById(id)) {
            carService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Car> update(@PathVariable("id") int id, @RequestBody Car car) {
        Optional<Car> byId = carService.findById(id);
        if (byId.isEmpty()) {
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
        return ResponseEntity.ok(carService.create(carDb));
    }
}
