package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Item;
import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByRegion(Region region);
    List<Item> findAllByRegion_IdAndType(int regionId, Type type);
    Page<Item> findAllByType(Type type, Pageable pageable);

}
