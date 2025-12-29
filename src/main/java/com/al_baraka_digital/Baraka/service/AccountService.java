package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.model.Account;
import com.al_baraka_digital.Baraka.model.User;

import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(User user);
    Account getAccountByUser(User user);
    Account getAccountByNumber(String accountNumber);
    void updateBalance(Account account, BigDecimal amount);
}
