package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByRegion(Region region);
    List<Item> findAllByRegion_IdAndType(int regionId, Type type);
    Page<Item> findAllByType(Type type, Pageable pageable);

}
