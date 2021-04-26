package com.netcracker.mycosts.controllers;


import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.MonthCostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private MonthCostsService monthCostsService;

    @GetMapping("/last-month-stat")
    public ResponseEntity<Map<String, Double>> getStatisticForLastMonth(@AuthenticationPrincipal User user) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        Map<String, Double> monthCostsMap = monthCostsService.findMonthCostsByUserAndStartDate(user, startDate).stream()
                .collect(Collectors.groupingBy(monthCosts -> monthCosts.getCategory().getName(),
                        Collectors.summingDouble(MonthCosts::getAmountUSD)));
        return ResponseEntity.status(HttpStatus.OK).body(monthCostsMap);
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }
}
