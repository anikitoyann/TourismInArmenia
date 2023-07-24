package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.repository.BookingRepository;
import com.example.tourarmeniacommon.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookingRepository bookingRepository;
    @Override
    public void save(Book book) {
        bookingRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookingRepository.findAll();
    }
}
