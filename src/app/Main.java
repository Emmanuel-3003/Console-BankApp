package app;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {

        Scanner sc =  new Scanner(System.in);

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
                case "0" -> running = false;

            }
        }
    }
}
