package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPackagesRepository extends JpaRepository<TourPackage, Integer> {

    List<TourPackage> findByRegion(Region region);
    List<TourPackage> findAllByRegionId(int regionId);

}
