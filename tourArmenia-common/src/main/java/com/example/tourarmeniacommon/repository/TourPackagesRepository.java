package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Region;
import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPackagesRepository extends JpaRepository<TourPackage, Integer> {

    List<TourPackage> findByRegion(Region region);

}
