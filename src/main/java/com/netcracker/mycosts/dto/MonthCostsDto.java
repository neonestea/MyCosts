package com.netcracker.mycosts.dto;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;;
import java.time.LocalDate;

@Setter
@Getter
@Builder
public class MonthCostsDto {

    private int id;
    private LocalDate startDate;
    private double amount;
    private User user;
    private Category category;
    private Account account;
}
