package com.guinhofsilva.agregadorinvestimentos.mapper;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateAccountDto;
import com.guinhofsilva.agregadorinvestimentos.model.Account;
import com.guinhofsilva.agregadorinvestimentos.model.AccountStock;
import com.guinhofsilva.agregadorinvestimentos.model.BillingAddress;
import com.guinhofsilva.agregadorinvestimentos.model.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class AccountMapper {
    public static Account createAccount(CreateAccountDto createAccountDto, BillingAddress billingAddress, User user, List<AccountStock> accountStocks){
        return new Account(null, createAccountDto.description(), billingAddress, user, Instant.now(), accountStocks);
    }
}
