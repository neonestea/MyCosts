package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.MonthCostsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
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

    public List<MonthCosts> findMonthCostsByUser(User user) {
        return monthCostsRepository.findAllByUser(user);
    }

    public void save(MonthCosts monthCosts) {
       monthCostsRepository.save(monthCosts);
    }

    public List<MonthCosts> findMonthCostsByUserAndCategory(User user, Category category) {
        return monthCostsRepository.findMonthCostsByUserAndCategory(user, category);
    }

    public List<MonthCosts> findMonthCostsByUserAndStartDate(User user, LocalDate startDate) {
        return monthCostsRepository.findMonthCostsByUserAndStartDate(user, startDate);
    }

    public void delete(MonthCosts monthCost) {
        monthCostsRepository.delete(monthCost);
    }
}
