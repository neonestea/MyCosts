package com.netcracker.mycosts.activities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.repositories.RegularCostRepository;
import com.netcracker.mycosts.services.CostService;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.netcracker.mycosts.services.CurrencyExchangeRateService;
import com.netcracker.mycosts.services.RegularCostService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


@Component
//TODO MAKE CRON
public class CostsActivities {

    public static final int MONTHS_TO_STORE_COSTS = 1;

    private CostService costService;
    private RegularCostService regularCostService;
    private CurrencyExchangeRateService currencyExchangeRateService;

    @Scheduled(cron = "0 1 0 1 * *")
    public void regularCosts() {
        currencyExchangeRateService.getAndSaveExchangeRates();
        regularCostService.regularCostProcedure();
    }

    @Scheduled(cron = "0 1 0 1 * *")
    public void deleteCosts() {
        costService.deleteCostsProcedure();
    }



    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }

    @Autowired
    public void setCurrencyExchangeRateService(CurrencyExchangeRateService currencyExchangeRateService) {
        this.currencyExchangeRateService = currencyExchangeRateService;
    }
}
