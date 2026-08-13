package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {

        Scanner sc =  new Scanner(System.in);
        BankService bankService = new BankServiceImpl();

        boolean running = true;
        System.out.println("Welcome to Console Bank..");

        while(running){
            System.out.println("""
                        1) Open Account
                        2) Deposit
                        3) Withdraw
                        4) Transfer
                        5) Account Statement
                        6) List Account
                        7) Search Accounts by Customer Name
                        0) Exit
                    """);
            System.out.println("Please enter your choice: ");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE : " + choice);

            switch (choice){
                case "1" -> openAccount(sc, bankService);
                case "2" -> deposit(sc, bankService);
                case "3" -> withdraw(sc, bankService);
                case "4" -> transfer(sc, bankService);
                case "5" -> statement(sc);
                case "6" -> listAccount(sc, bankService);
                case "7" -> searchAccount(sc);
                case "0" -> running = false;

            }
        }
    }

    private static void openAccount(Scanner sc, BankService bankService) {
        System.out.println("Customer Name : ");
        String name = sc.nextLine().trim();

        System.out.println("Customer Email : ");
        String email = sc.nextLine().trim();

        System.out.println("Account Type(CURRENT/SAVINGS) : ");
        String type = sc.nextLine().trim();

        System.out.println("Initial Deposit(OPTIONAL) : ");
        Double initial = Double.valueOf(sc.nextLine().trim());

        String accountNumber = bankService.openAccount(name, email, type);
        if(initial > 0){
            bankService.deposit(accountNumber, initial, "Deposit");
        }
        System.out.println("Account Opened Successfully.\nYour account number is " + accountNumber);
    }

    private static void deposit(Scanner sc, BankService bankService) {
        System.out.println("Account Number : ");
        String accountNumber = sc.nextLine().trim();

        System.out.println("Amount : ");
        Double amount = Double.valueOf(sc.nextLine().trim());

        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.println(amount + " deposited to AC : " + accountNumber);

    }

    private static void withdraw(Scanner sc, BankService bankService) {
        System.out.println("Account Number : ");
        String accountNumber = sc.nextLine().trim();

        System.out.println("Amount : ");
        Double amount = Double.valueOf(sc.nextLine().trim());

        bankService.withdraw(accountNumber, amount, "Withdrawal");
        System.out.println(amount + " withdraw from AC : " + accountNumber);
    }

    private static void transfer(Scanner sc, BankService bankService) {
        System.out.println("From Account Number : ");
        String from = sc.nextLine().trim();

        System.out.println("To Account Number : ");
        String to = sc.nextLine().trim();

        System.out.println("Amount : ");
        Double amount = Double.valueOf(sc.nextLine().trim());

        bankService.transfer(from, to, amount, "Transfer");
        System.out.println(amount + " transferred to AC : " + to + " from AC : " + from);
    }

    private static void statement(Scanner sc) {
    }

    private static void listAccount(Scanner sc, BankService bankService) {
        bankService.listAccounts().forEach( a ->{
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

    private static void searchAccount(Scanner sc) {
    }
}
