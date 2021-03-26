package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.MonthCostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MonthCostsService {

    private MonthCostsRepository monthCostsRepository;

    @Autowired
    public void setMonthCostsRepository(MonthCostsRepository monthCostsRepository) {
        this.monthCostsRepository = monthCostsRepository;
    }

    public MonthCosts findMonthCostsByUserAndAccountAndCategoryAndStartDate(User user, Account account, Category category,
                                                                     LocalDate startDate) {
        return monthCostsRepository.findMonthCostsByUserAndAccountAndCategoryAndStartDate(user, account, category, startDate);
    }

    public void save(MonthCosts monthCosts) {
        monthCostsRepository.save(monthCosts);
    }
}
