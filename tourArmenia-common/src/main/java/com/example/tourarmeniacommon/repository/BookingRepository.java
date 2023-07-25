package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Book,Integer> {
}
