package com.yaelev.bank.model;

import javax.persistence.*;

@Entity
@Table
public class TransactionAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "accountNo")
    private String accountNo;

    @Column(name = "balance")
    private double balance;

    // @Transient
    @ManyToOne
    private Customer owner;

    // Constructor, creating account without assigning it to an owner


    public TransactionAccount() {
    }

    // Constructor when no owner is set at start
    public TransactionAccount(String accountNo) {
        setAccountNo(accountNo);
    }

    // Constructor when both account number and owner is set at start
    public TransactionAccount(String accountNo, Customer owner) {
        setAccountNo(accountNo);
        this.owner = owner;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        // Check if only 0-9 (chars) are entered
        boolean onlyNums = false;
        for (char ch : accountNo.toCharArray()) {
            switch (ch) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> onlyNums = true;
                default -> onlyNums = false;
            }
        }
        // If account number is 9 characters and only numbers, accept it
        if (accountNo.length() >= 9 && onlyNums) {
            this.accountNo = accountNo;
        } else {
            throw new IllegalArgumentException("Account number must be at least 9 digits");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount, boolean depositOrWithdrawal) {
        if (depositOrWithdrawal && amount < 1000000) { // Deposit& max 1 million USD
            balance += amount;
        } else if (!depositOrWithdrawal && balance >= amount) { // Withdrawal
            balance -= amount;
        } else if (!depositOrWithdrawal && balance < amount) { // Withdrawal but not enough money
            System.out.println("Cant withdraw money - You only have " + balance + " USD in your acount.");
        } else {
            System.out.println("Sorry, something went wrong");
        }
    }

    public Customer getOwner() {
        return owner;
    }

    // E.g. to assign an already created account to a customer
    public void setOwner(Customer owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "TransactionAccount{" +
                "id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}
