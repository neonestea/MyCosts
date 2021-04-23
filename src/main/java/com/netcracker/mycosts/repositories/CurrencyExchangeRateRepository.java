package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Integer> {

     //TODO почему distinct не работает?
     List<CurrencyExchangeRate> findDistinctByCurrencyAndDate(Currency currency, LocalDate date);
}
