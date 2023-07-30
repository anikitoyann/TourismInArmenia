package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Book,Integer> {
    List<Book> findByUser(User user);
    Optional<Book> findByToken(String token);

}
