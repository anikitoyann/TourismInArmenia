package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Hotel;
import com.example.tourisminarmenia.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findByRegion(Region region);
}
