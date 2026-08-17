package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import exception.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository =  new AccountRepository();
    private final TransactionRepository transactionRepository =  new TransactionRepository();
    private final CustomerRepository customerRepository =  new CustomerRepository();

    private final Validation<String> validateName = name -> {
        if(name == null || name.isBlank()) throw new ValidationException("Name cannot be empty..");
    };

    private final Validation<String> validateEmail = email -> {
        if(email == null || !email.contains("@")) throw new ValidationException("Email cannot be empty..");
    };

    private final Validation<String> validateType = type -> {
        if(type == null || !(type.equalsIgnoreCase("SAVINGS") || type.equalsIgnoreCase("CURRENT")))
            throw new ValidationException("Type must be SAVINGS or CURRENT..");
    };

    private final Validation<Double> validateAmountPositive = amount -> {
        if(amount == null || amount < 0) throw new ValidationException("Please enter valid Amount..");
    };

    @Override
    public String openAccount(String name, String email, String accountType) {

        validateName.validate(name);
        validateEmail.validate(email);
        validateType.validate(accountType);

        String customerId = UUID.randomUUID().toString();
        Customer c = new Customer(customerId, name, email);
        customerRepository.save(c);

        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber, customerId, (double) 0, accountType);
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream().sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note){

        validateAmountPositive.validate(amount);

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountNumber));

        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.DEPOSIT, account.getAccountNumber(),
                amount, LocalDateTime.now(), note);
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {

        validateAmountPositive.validate(amount);

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + accountNumber));

        if(account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction(UUID.randomUUID().toString(), Type.WITHDRAW, account.getAccountNumber(),
                amount, LocalDateTime.now(), note);
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {

        validateAmountPositive.validate(amount);

        if(fromAcc.equals(toAcc)) {
            throw new ValidationException("Cannot transfer to your own account..");
        }
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found : " + toAcc));
        if(from.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction fromTransaction = new Transaction(UUID.randomUUID().toString(), Type.TRANSFER_OUT, from.getAccountNumber(),
                amount, LocalDateTime.now(), note);
        transactionRepository.add(fromTransaction);
        Transaction toTransaction = new Transaction(UUID.randomUUID().toString(), Type.TRANSFER_IN, to.getAccountNumber(),
                amount, LocalDateTime.now(), note);
        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimeStamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
        List<Account> result = new ArrayList<>();

        for(Customer c : customerRepository.findAll()){
            if(c.getCustomerName().toLowerCase().contains(query)){
                result.addAll(accountRepository.findCustomerById(c.getId()));
            }
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size()+1;
        return String.format("AC%06d", size);
    }
}
