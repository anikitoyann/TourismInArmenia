package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.User;
import com.example.tourarmeniacommon.repository.BookingRepository;
import com.example.tourarmeniacommon.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Book> findByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public Optional<Book>  findByToken(String token){
        return bookingRepository.findByToken(token);
    }
}
