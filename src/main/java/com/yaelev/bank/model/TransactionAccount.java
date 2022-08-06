package com.yaelev.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
@Table
public class TransactionAccount {

    @Id
    @SequenceGenerator(
            name = "transactionaccount_sequence",
            sequenceName = "transactionaccount_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionaccount_sequence")
    private long id;

    @Column(name = "accountNo")
    private String accountNo;

    @Column(name = "balance")
    private double balance;

    // "MANY accounts can belong to ONE customer"
    @ManyToOne
    private Customer customer;

    public TransactionAccount() {
    }

    // Constructor when no customer/owner is set at start
    public TransactionAccount(String accountNo, double balance) {
        setAccountNo(accountNo);
        this.balance = balance;
    }

    // Constructor when customer/owner is set at start
    public TransactionAccount(String accountNo, double balance, Customer customer) {
        setAccountNo(accountNo);
        this.balance = balance;
        this.customer = customer;
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
    public void setBalance(double amount) {
        this.balance += amount;
    }

    @JsonBackReference // Solves the infinite recursion problem
    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "TransactionAccount{" +
                "id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", customer=" + customer +
                '}';
    }
}
