package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private CarServiceImpl carService;
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    void findAll() {
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car();
        car1.setId(1);
        car1.setName("Car 1");
        car1.setSeats("4");
        Car car2 = new Car();
        car2.setId(2);
        car2.setName("Car 2");
        car2.setSeats("7");
        cars.add(car1);
        cars.add(car2);
        when(carRepository.findAll()).thenReturn(cars);
        List<Car> result = carService.findAll();
        assertEquals(cars.size(), result.size());
        assertEquals(car1.getName(), result.get(0).getName());
        assertEquals(car2.getName(), result.get(1).getName());
        assertEquals(car1.getSeats(), result.get(0).getSeats());
        assertEquals(car2.getSeats(), result.get(1).getSeats());
    }

    @Test
    void save() throws IOException {
        Car carToSave = new Car();
        carToSave.setName("New Car");
        carToSave.setSeats("5");
        when(carRepository.save(carToSave)).thenReturn(carToSave);
        carService.save(null, carToSave);
        ArgumentCaptor<Car> carCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(1)).save(carCaptor.capture());
        Car savedCar = carCaptor.getValue();
        assertEquals(carToSave.getName(), savedCar.getName());
        assertEquals(carToSave.getSeats(), savedCar.getSeats());
    }

    @Test
    void findById() {
        int carIdToFind = 1;
        Car car = new Car();
        car.setId(carIdToFind);
        car.setName("Car 1");
        car.setSeats("4");
        when(carRepository.findById(carIdToFind)).thenReturn(Optional.of(car));
        Optional<Car> result = carService.findById(carIdToFind);
        assertTrue(result.isPresent());
        assertEquals(carIdToFind, result.get().getId());
        assertEquals(car.getName(), result.get().getName());
        assertEquals(car.getSeats(), result.get().getSeats());
    }

    @Test
    void create() {
        Car carToCreate = new Car();
        carToCreate.setName("New Car");
        carToCreate.setSeats("5");
        when(carRepository.save(carToCreate)).thenReturn(carToCreate);
        Car result = carService.create(carToCreate);
        assertNotNull(result);
        assertEquals(carToCreate.getName(), result.getName());
        assertEquals(carToCreate.getSeats(), result.getSeats());
    }

    @Test
    void existsById() {
        int carIdToCheck = 1;
        when(carRepository.existsById(carIdToCheck)).thenReturn(true);
        boolean result = carService.existsById(carIdToCheck);
        assertTrue(result);
    }

    @Test
    void deleteById() {
        int carIdToDelete = 1;
        carService.deleteById(carIdToDelete);
        verify(carRepository, times(1)).deleteById(carIdToDelete);
    }

    @Test
    void updateCar() {
        int carIdToUpdate = 1;
        Car carToUpdate = new Car();
        carToUpdate.setId(carIdToUpdate);
        carToUpdate.setName("Updated Car");
        carToUpdate.setSeats("6");
        Optional<Car> optionalCar = Optional.of(carToUpdate);

        when(carRepository.findById(carIdToUpdate)).thenReturn(optionalCar);
        when(carRepository.save(carToUpdate)).thenReturn(carToUpdate);
        Car result = carService.updateCar(carToUpdate, optionalCar);
        assertNotNull(result);
        assertEquals(carIdToUpdate, result.getId());
        assertEquals(carToUpdate.getName(), result.getName());
        assertEquals(carToUpdate.getSeats(), result.getSeats());
    }
}