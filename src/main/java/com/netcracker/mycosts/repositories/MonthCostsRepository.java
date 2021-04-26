package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthCostsRepository extends JpaRepository<MonthCosts, Integer> {

    MonthCosts findMonthCostsByUserAndAccountAndCategoryAndStartDate(User user, Account account, Category category,
                                                                    LocalDate startDate);

    List<MonthCosts> findAllByUser(User user);

    List<MonthCosts> findMonthCostsByUserAndCategory(User user, Category category);

    List<MonthCosts> findMonthCostsByUserAndStartDate(User user, LocalDate startDate);
}
