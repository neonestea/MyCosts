package com.netcracker.mycosts.controllers;


import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.services.MonthCostsService;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private MonthCostsService monthCostsService;
    private UserService userService;

    @GetMapping("/last-month-stat")
    public ResponseEntity<Map<String, Double>> getStatisticForLastMonth(@AuthenticationPrincipal User user) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        Map<String, Double> monthCostsMap = monthCostsService.findMonthCostsByUserAndStartDate(user, startDate).stream()
                .filter(monthCosts -> monthCosts.getAmountUSD() > 0)
                .collect(Collectors.groupingBy(monthCosts -> monthCosts.getCategory().getName(),
                        Collectors.summingDouble(MonthCosts::getAmountUSD)));
        return ResponseEntity.status(HttpStatus.OK).body(monthCostsMap);
    }

    @GetMapping("/year-stat")
    public ResponseEntity<List<CategoryAmounts>> getStatistic(@AuthenticationPrincipal User user) {
        user = userService.getUserById(user.getId());
        Set<Category> userCategories = user.getCategories();
        LocalDate startDate = LocalDate.of(LocalDate.now().minusYears(1).getYear(),
                LocalDate.now().getMonth(), 1);
        List<MonthCosts> monthCostsList = monthCostsService.findMonthCostsByUserAndStartDate(user, startDate).stream()
                .filter(monthCosts -> monthCosts.getAmountUSD() > 0)
                .sorted(Comparator.comparing(MonthCosts::getStartDate))
                .collect(Collectors.toList());
        List<CategoryAmounts> categoryAmountsList = new ArrayList<>();

        userCategories.forEach(category -> {

            //categoryAmounts.setCategory(category);
            final List<Double> amountsByCategoryFromDate = getAmountsByCategoryFromDate(category, startDate, monthCostsList);
            CategoryAmounts categoryAmounts = new CategoryAmounts(category, amountsByCategoryFromDate);
            //categoryAmounts.setAmounts();
            categoryAmountsList.add(categoryAmounts);
        });
        return ResponseEntity.status(HttpStatus.OK).body(categoryAmountsList);
    }

    @GetMapping("/year-months")
    public ResponseEntity<List<LocalDate>> getMonthsOfLastYear() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = LocalDate.of(LocalDate.now().minusYears(1).getYear(),
                LocalDate.now().getMonth(), 1);

        while (date.compareTo(LocalDate.now()) < 0) {
            dates.add(date);
            date = date.plusMonths(1);
        }

        return ResponseEntity.status(HttpStatus.OK).body(dates);

    }


    private List<Double> getAmountsByCategoryFromDate(Category category, LocalDate date,
                                                      List<MonthCosts> monthCostsList) {
        List<Double> amounts = new ArrayList<>();
        while (date.compareTo(LocalDate.now()) <= 0) {
            MonthCosts monthCosts = getMonthCostsByCategoryAndDate(category, date, monthCostsList);
            if (monthCosts == null) {
                amounts.add(0d);
            } else {
                amounts.add(monthCosts.getAmountUSD());
            }
            date = date.plusMonths(1);
        }
        return amounts;
    }

    private MonthCosts getMonthCostsByCategoryAndDate(Category category, LocalDate date,
                                                      List<MonthCosts> monthCostsList) {
        for (MonthCosts monthCosts: monthCostsList) {
            if (monthCosts.getStartDate().equals(date) && monthCosts.getCategory().equals(category)) {
                System.out.println("BINGO");
                return monthCosts;
            }
        }
        return null;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
