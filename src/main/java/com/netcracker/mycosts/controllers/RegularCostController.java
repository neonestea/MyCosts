package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.CurrencyExchangeRate;
import com.netcracker.mycosts.entities.RegularCost;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.services.CurrencyExchangeRateService;
import com.netcracker.mycosts.services.RegularCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class RegularCostController {

    private CurrencyExchangeRateService currencyExchangeRateService;
    private RegularCostService regularCostService;
    private CostService costService;

    @PostMapping("/regular_costs")
    public ResponseEntity<RegularCost> addRegularCost(@RequestBody RegularCost regularCost,
                                                      @AuthenticationPrincipal User user) {
        regularCost.setUser(user);
        regularCost.setPayDay(regularCost.getNextDate().getDayOfMonth());
        regularCost.setCurrency(regularCost.getAccount().getCurrency());
        if (regularCost.getNextDate().compareTo(LocalDate.now()) == 0) {
            regularCost.setLastDate(regularCost.getNextDate());
            regularCost.setNextDate(getNextDate(regularCost));
            costService.save(costFromRegularCost(regularCost));
        }
        regularCostService.save(regularCost);
        return ResponseEntity.status(HttpStatus.CREATED).body(regularCost);
    }

    @DeleteMapping("/regular_costs/{id}")
    public ResponseEntity<RegularCost> deleteRegularCost(@PathVariable int id,
                                                         @AuthenticationPrincipal User user) {
        regularCostService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private LocalDate getNextDate(RegularCost regularCost) {
        LocalDate nextDate;
        if (regularCost.isEveryMonth()) {
            nextDate = LocalDate.now().plusMonths(1);
            nextDate = nextDate.getDayOfMonth() < regularCost.getPayDay() ?  LocalDate.of(nextDate.getYear(), nextDate.getMonth(), regularCost.getPayDay())
                    : nextDate;
        } else {
            nextDate = LocalDate.now().plusDays(regularCost.getPeriod());
        }
        return nextDate;
    }

    private Cost costFromRegularCost(RegularCost regularCost) {
        CurrencyExchangeRate currencyExchangeRate =
                currencyExchangeRateService.findByCurrencyAndDate(regularCost.getCurrency()
                        ,regularCost.getLastDate());
        return Cost.builder()
                .user(regularCost.getUser())
                .amount(regularCost.getAmount())
                .amountUSD(regularCost.getAmount() * currencyExchangeRate.getRate())
                .date(LocalDate.now())
                .account(regularCost.getAccount())
                .category(regularCost.getCategory())
                .build();
    }

    @Autowired
    public void setRegularCostService(RegularCostService regularCostService) {
        this.regularCostService = regularCostService;
    }

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @Autowired
    public void setCurrencyExchangeRateService(CurrencyExchangeRateService currencyExchangeRateService) {
        this.currencyExchangeRateService = currencyExchangeRateService;
    }
}