package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Integer> {

     List<CurrencyExchangeRate> findByCurrencyAndDate(Currency currency, LocalDate date);
}