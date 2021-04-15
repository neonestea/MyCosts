package com.netcracker.mycosts.services;

import java.time.LocalDate;
import java.util.List;

import com.netcracker.mycosts.entities.*;
import com.netcracker.mycosts.repositories.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CostService {

    private CostRepository costRepository;
    private AccountService accountService;
    private MonthCostsService monthCostsService;

    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }

    public List<Cost> getAll(String userId) {
        return costRepository.findCostByUserId(userId);
    }

    public void deleteCostById(int id) {
        costRepository.deleteCostById(id);
    }

    public Cost getCostById(int id) {
        return costRepository.findById(id).get();
    }

    public void save(Cost cost) {
        int accountId = cost.getAccount().getId();
        Account account = accountService.getAccountById(accountId);
        double newAmount = account.getAmount() - cost.getAmount();
        account.setAmount(newAmount);
        createOrUpdateMonthCosts(cost);
        accountService.save(account);
        costRepository.save(cost);
    }

    public void deleteAllWithCreationDateTimeBefore(LocalDate minusMonths) {
        costRepository.deleteAllWithCreationDateTimeBefore(minusMonths);
    }

    private void createOrUpdateMonthCosts(Cost cost) {
        User user = cost.getUser();
        LocalDate costDate = cost.getDate();
        Account account = cost.getAccount();
        Category category = cost.getCategory();
        double amount = cost.getAmount();

        LocalDate startDate = LocalDate.of(costDate.getYear(), costDate.getMonth(), 1);
        MonthCosts monthCost = monthCostsService.findMonthCostsByUserAndAccountAndCategoryAndStartDate(user, account, category,
                startDate);

        if (monthCost == null) {
            monthCost = MonthCosts.builder()
                    .user(user)
                    .account(account)
                    .startDate(startDate)
                    .category(category)
                    .amount(amount)
                    .build();
        } else {
            monthCost.addCostAmount(amount);
        }

        monthCostsService.save(monthCost);
    }
}
