package com.netcracker.mycosts.controllers;

import java.time.LocalDate;
import java.util.List;

import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
public class CostController {

    private CostService costService;
    private AccountService accountService;
    private MonthCostsService monthCostsService;

    @PostMapping("/costs")
    public ResponseEntity<Cost> addCost(@RequestBody Cost cost, @AuthenticationPrincipal User user) {
        cost.setUser(user);
        costService.save(cost);
        return ResponseEntity.status(HttpStatus.CREATED).body(cost);
    }

    @DeleteMapping("/costs/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal User user) {
        Cost cost = costService.getCostById(id);
        Account account = cost.getAccount();
        int accountId = account.getId();
        double amount = cost.getAmount();
        double amountUSD = cost.getAmountUSD();
        Category category = cost.getCategory();
        double newAmount = account.getAmount() + amount;
        Account accountFromDB = accountService.getAccountById(accountId);
        accountFromDB.setAmount(newAmount);
        LocalDate date = cost.getDate();
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);

        MonthCosts monthCost = monthCostsService.findMonthCostsByUserAndAccountAndCategoryAndStartDate(user, account, category,
                startDate);
        if (monthCost.getAmount() - amount > 0) {
            monthCost.setAmount(monthCost.getAmount() - amount);
            monthCost.setAmountUSD(monthCost.getAmountUSD() - amountUSD);
            monthCostsService.save(monthCost);
        } else {
            monthCostsService.delete(monthCost);
        }
        accountService.save(accountFromDB);
        costService.deleteCostById(id);
    }

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }
}
