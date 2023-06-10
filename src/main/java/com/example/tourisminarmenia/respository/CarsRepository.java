package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, Integer> {

}
