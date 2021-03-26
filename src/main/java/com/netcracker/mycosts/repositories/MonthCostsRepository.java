package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MonthCostsRepository extends JpaRepository<MonthCosts, Integer> {

    MonthCosts findMonthCostsByUserAndAccountAndCategoryAndStartDate(User user, Account account, Category category,
                                                                    LocalDate startDate);
}
