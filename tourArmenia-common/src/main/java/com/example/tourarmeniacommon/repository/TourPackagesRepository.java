package com.example.tourisminarmenia.respository;

import com.example.tourisminarmenia.entity.Region;
import com.example.tourisminarmenia.entity.TourPackage;
import com.example.tourarmeniacommon.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourPackagesRepository extends JpaRepository<TourPackage, Integer> {

}
