package com.netcracker.mycosts.services;

import com.netcracker.mycosts.repositories.CurrencyExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeRateService {

    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Autowired
    public void setCurrencyExchangeRateRepository(CurrencyExchangeRateRepository currencyExchangeRateRepository) {
        this.currencyExchangeRateRepository = currencyExchangeRateRepository;
    }
}
