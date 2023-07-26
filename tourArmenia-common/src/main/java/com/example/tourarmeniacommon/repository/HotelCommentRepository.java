package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.HotelComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelCommentRepository extends JpaRepository<HotelComment,Integer> {
    List<HotelComment> findAllByItemId(int hotelId);
}
