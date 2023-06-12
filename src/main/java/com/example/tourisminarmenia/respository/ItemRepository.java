package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByRegion(Region region);
}
