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
    private UserService userService;
    private AccountService accountService;
    private MonthCostsService monthCostsService;
    private CategoryService categoryService;

    @GetMapping("/users/{userId}/costs")
    public List<Cost> allCosts(@PathVariable int userId) {
        return costService.getAll(userId);
    }

    @PostMapping("/costs")
    public ResponseEntity<Cost> addCost(@RequestBody Cost cost, @AuthenticationPrincipal User user) {
        LocalDate date = cost.getDate();
        Account account = cost.getAccount();
        Category category = cost.getCategory();
        double amount = cost.getAmount();

        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);

        MonthCosts monthCost = monthCostsService.findMonthCostsByUserAndAccountAndCategoryAndStartDate(user, account, category,
                startDate);

        if (monthCost == null) {
            monthCost = MonthCosts.builder()
                    .user(user)
                    .account(account)
                    .category(category)
                    .amount(amount)
                    .build();
        } else {
            monthCost.addCostAmount(amount);
        }

        monthCostsService.save(monthCost);
        costService.save(cost);
        return ResponseEntity.status(HttpStatus.CREATED).body(cost);
    }

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
