package service.impl;

import domain.Account;
import service.BankService;

import java.util.UUID;

public class BankServiceImpl implements BankService {
    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        String accountNumber = UUID.randomUUID().toString();
        Account a = new Account(accountNumber, accountType, (double) 0, customerId);
        return "";
    }
}
