package com.netcracker.mycosts.dto;

import com.netcracker.mycosts.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthCostsDto {
    private LocalDate startDate;
    private double amount;
    private double amountUSD;
    private Category category;
    private Account account;

    public static MonthCostsDto convertFromMonthCosts(MonthCosts monthCosts) {
        return MonthCostsDto.builder()
                .startDate(monthCosts.getStartDate())
                .amount(monthCosts.getAmount())
                .category(monthCosts.getCategory())
                .account(monthCosts.getAccount())
                .build();
    }
}
