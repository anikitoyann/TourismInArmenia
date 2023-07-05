package com.example.tourarmeniacommon.repository;


import com.example.tourarmeniacommon.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
