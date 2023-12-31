package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Currency;
import com.example.tourarmeniacommon.repository.CurrencyRepository;
import com.example.tourarmeniacommon.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    @Override
    // Retrieves a list of all currencies from the currency repository.
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    //Saves a currency object to the currency repository.
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }
}
