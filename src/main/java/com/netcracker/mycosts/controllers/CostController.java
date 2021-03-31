package com.netcracker.mycosts.controllers;

import java.time.LocalDate;
import java.util.List;

import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.services.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/users/{userId}/accounts/{accountId}/costs")
    public Cost addCost(@PathVariable String userId, @PathVariable int accountId, @RequestParam double amount,
                        @NotNull String categoryName) {
        LocalDate currentDate = LocalDate.now();
        User user = userService.getUserById(userId);
        Account account = accountService.getAccountById(accountId);
        Category category = categoryService.findCategoryByName(categoryName);
        Cost cost = Cost.builder().user(user).account(account).date(currentDate).build();
        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
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
        return cost;
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
