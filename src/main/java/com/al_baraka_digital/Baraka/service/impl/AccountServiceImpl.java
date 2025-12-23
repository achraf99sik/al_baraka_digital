package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.exception.AlreadyExistsException;
import com.al_baraka_digital.Baraka.exception.ResourceNotFoundException;
import com.al_baraka_digital.Baraka.model.Account;
import com.al_baraka_digital.Baraka.model.User;
import com.al_baraka_digital.Baraka.repository.AccountRepository;
import com.al_baraka_digital.Baraka.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(User user) {
        if (accountRepository.findByOwner(user).isPresent()) {
            throw new AlreadyExistsException("User already has an account");
        }

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .balance(BigDecimal.ZERO)
                .owner(user)
                .build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByUser(User user) {
        return accountRepository.findByOwner(user)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for user"));
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountNumber));
    }

    @Override
    @Transactional
    public void updateBalance(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}
