package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, Integer> {

}
