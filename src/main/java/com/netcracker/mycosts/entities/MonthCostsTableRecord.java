package com.netcracker.mycosts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthCostsTableRecord {
    private double amount;
    private String category;
    private String account;
    private Currency currency;

    public static MonthCostsTableRecord fromMonthCosts(MonthCosts monthCosts) {
        return new MonthCostsTableRecord(
                monthCosts.getAmount(), monthCosts.getCategory().getName(), monthCosts.getAccount().getName(),
                monthCosts.getAccount().getCurrency()
        );
    }
}
