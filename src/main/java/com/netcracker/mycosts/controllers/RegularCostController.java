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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
        regularCost.setCurrency(regularCost.getAccount().getCurrency());
        costService.save(costFromRegularCost(regularCost));
        regularCostService.save(regularCost);
        return ResponseEntity.status(HttpStatus.CREATED).body(regularCost);
    }


    public Cost costFromRegularCost(RegularCost regularCost) {
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


    @DeleteMapping("/regular_costs")
    public ResponseEntity<RegularCost> deleteRegularCost(@RequestParam int regularCostId,
                                                         @AuthenticationPrincipal User user) {
        regularCostService.deleteById(regularCostId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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