package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Integer> {
}
