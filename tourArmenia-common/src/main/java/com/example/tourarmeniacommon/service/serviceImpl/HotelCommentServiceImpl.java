package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.HotelComment;
import com.example.tourarmeniacommon.repository.HotelCommentRepository;
import com.example.tourarmeniacommon.service.HotelCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelCommentServiceImpl implements HotelCommentService {
    private final HotelCommentRepository hotelCommentRepository;

    @Override
    public List<HotelComment> findAllByItemId(int id) {
        return hotelCommentRepository.findAllByItemId(id);
    }

    @Override
    public void save(HotelComment hotelComment) {
        hotelCommentRepository.save(hotelComment);
    }
}
