package com.netcracker.mycosts.dto;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.RegularCost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegularCostDto {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextDate;
    private String currency;
    private int payDay;
    private int period;
    private boolean everyMonth;
    private double amount;
    private Category category;
    private Account account;

    public RegularCostDto(RegularCost regularCost) {
        this.name = regularCost.getName();
        this.lastDate = regularCost.getLastDate();
        this.nextDate = regularCost.getNextDate();
        this.currency = regularCost.getCurrency().name();
        this.payDay = regularCost.getPayDay();
        this.period = regularCost.getPeriod();
        this.everyMonth = regularCost.isEveryMonth();
        this.amount = regularCost.getAmount();
        this.category = regularCost.getCategory();
        this.account = regularCost.getAccount();
    }

    public RegularCost convertToRegularCost() {
        return  RegularCost.builder()
                .name(this.name)
                .lastDate(this.lastDate)
                .nextDate(this.nextDate)
                .currency(this.account.getCurrency())
                .payDay(this.payDay)
                .period(this.period)
                .everyMonth(this.everyMonth)
                .amount(this.amount)
                .category(this.category)
                .account(this.account)
                .build();
    }

    public static RegularCostDto convertFromRegularCost(RegularCost regularCost) {
        return RegularCostDto.builder()
                .name(regularCost.getName())
                .lastDate(regularCost.getLastDate())
                .nextDate(regularCost.getNextDate())
                .currency(regularCost.getCurrency().name())
                .payDay(regularCost.getPayDay())
                .period(regularCost.getPeriod())
                .everyMonth(regularCost.isEveryMonth())
                .amount(regularCost.getAmount())
                .category(regularCost.getCategory())
                .account(regularCost.getAccount())
                .build();
    }
}
