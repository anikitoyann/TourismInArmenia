package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> findAll();

    public Currency save(Currency currency);
}
