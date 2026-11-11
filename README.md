# Simple Banking System

A simple Java Swing application for creating bank accounts, depositing and withdrawing money, and viewing account balances and transaction history. Account and transaction data are stored in fixed-size arrays and remain in memory while the application is open.

## Run

From this folder, compile and launch the application:

```text
javac *.java
java Main
```

## Classes and course concepts

- `Bank` manages an array of `BankAccount` objects and checks for duplicate account numbers.
- `BankAccount` stores account details, manages its balance, and records transactions in an array.
- `Transaction` represents a deposit or withdrawal.
- `BankingSystemGUI` provides the Swing interface and input validation.
- `Main` starts the interface on Swing's event-dispatch thread.

The project demonstrates constructors, private fields, getters, object creation, arrays, `if`/`else`, a `switch`, and `for`/`while` loops.
