package com.netcracker.mycosts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MonthCostsView {
    private LocalDate date;
    private double amountUSD;
    private Category category;

    public static MonthCostsView fromMonthCosts(MonthCosts monthCosts) {
        return new MonthCostsView(monthCosts.getStartDate(), monthCosts.getAmountUSD(), monthCosts.getCategory());
    }
}
