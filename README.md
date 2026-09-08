# Bank App — Console Banking System (Java)

> 🚧 **Project status: under active development.** This is currently a console-based (CLI) application with in-memory storage — no database persistence and no UI yet. A graphical/web UI is planned.

A simple banking system built in **plain Java** (no frameworks) that runs entirely in the terminal. It supports opening accounts, deposits, withdrawals, transfers, transaction statements, and customer search — all backed by in-memory data structures.

## Features

- 👤 **Open accounts** — create a customer and an associated account (Current/Savings) with an optional initial deposit
- 💰 **Deposit & Withdraw** — update account balances with insufficient-funds protection on withdrawals
- 🔁 **Transfer** — move funds between two accounts (blocks transfers to the same account)
- 🧾 **Account statement** — view the chronological transaction history for an account
- 📋 **List accounts** — view all accounts sorted by account number
- 🔍 **Search by customer name** — find accounts belonging to customers whose name contains a search term

## Tech Stack

| Layer        | Technology                     |
|--------------|----------------------------------|
| Language     | Java 17+ (uses switch expressions & text blocks) |
| Storage      | In-memory (`HashMap`-backed repositories) |
| Interface    | Console / CLI (`Scanner`)         |
| Build        | None currently — plain `.java` sources / IntelliJ module |

## Project Structure

```
src/
├── app/
│   └── Main.java                  # CLI entry point and menu loop
├── domain/
│   ├── Account.java                # Account entity
│   ├── Customer.java                # Customer entity
│   ├── Transaction.java             # Transaction entity
│   └── Type.java                    # Transaction type enum (DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT)
├── repository/
│   ├── AccountRepository.java       # In-memory account storage
│   ├── CustomerRepository.java      # In-memory customer storage
│   └── TransactionRepository.java   # In-memory transaction storage
└── service/
    ├── BankService.java             # Business logic interface
    └── impl/
        └── BankServiceImpl.java     # Business logic implementation
```

## Getting Started

### Prerequisites

- JDK 17 or later

### Compile & Run

From the `src` directory:

```bash
# Compile
javac -d out $(find . -name "*.java")

# Run
java -cp out app.Main
```

Or open the project in IntelliJ IDEA (a `.iml` module file is included) and run `Main.java` directly.

### Usage

On launch you'll see a menu:

```
Welcome to Console Bank..
1) Open Account
2) Deposit
3) Withdraw
4) Transfer
5) Account Statement
6) List Account
7) Search Accounts by Customer Name
0) Exit
```

Enter the number corresponding to the action you want, then follow the prompts.

## Data Model

- **Customer** — `id`, `customerName`, `email`
- **Account** — `accountNumber` (auto-generated, e.g. `AC000001`), `customerId`, `balance`, `accountType`
- **Transaction** — `id`, `type`, `accountNumber`, `amount`, `timeStamp`, `note`

Note: all data is held in memory only — it resets every time the application restarts.

## Roadmap

- [ ] Add a UI (web or desktop) instead of the console interface
- [ ] Persistent storage (database) instead of in-memory repositories
- [ ] Add a build tool (Maven/Gradle) for dependency management and packaging
- [ ] Add input validation (e.g. reject invalid account types, non-numeric amounts)
