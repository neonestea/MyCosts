package com.netcracker.mycosts.dto;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @NotBlank
    private String name;
    private double amount;
    @NotBlank
    private String currency;
    private boolean active;

    public Account convertToAccount() {
        return Account.builder()
                .name(this.name)
                .amount(this.amount)
                .active(this.active)
                .currency(Currency.valueOf(this.currency))
                .build();
    }

    public static AccountDto convertFromAccount(Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .amount(account.getAmount())
                .active(account.getActive())
                .currency(account.getCurrency().name())
                .build();
    }
}
