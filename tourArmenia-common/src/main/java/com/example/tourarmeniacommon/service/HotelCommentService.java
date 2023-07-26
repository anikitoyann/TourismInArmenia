package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.HotelComment;

import java.util.List;

public interface HotelCommentService {
    List<HotelComment> findAllByItemId(int id);
    void save(HotelComment hotelComment);
}
