package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, QuerydslPredicateExecutor<Item> {

    List<Item> findByRegion(Region region);
    Page<Item> findAllByRegion_IdAndType(Pageable pageable,int regionId, Type type);
    Page<Item> findAllByType(Type type, Pageable pageable);

}
