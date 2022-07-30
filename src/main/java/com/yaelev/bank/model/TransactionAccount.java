package com.yaelev.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
@Table //(name="transactionaccount", schema = "bank")
public class TransactionAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "accountNo")
    private String accountNo;

    @Column(name = "balance")
    private double balance;

    // "MANY accounts can belong to ONE customer"
    @ManyToOne
    @JsonBackReference // Solves the infinite recursion problem
    // @JoinColumn(name = "customer_id")
    private Customer customer;

    public TransactionAccount() {
    }

    // Constructor when no customer/owner is set at start
    public TransactionAccount(String accountNo) {
        setAccountNo(accountNo);
    }

    // Constructor when both account number and customer/owner is set at start
    public TransactionAccount(String accountNo, double balance, Customer customer) {
        setAccountNo(accountNo);
        setBalance(balance);
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
        if ((amount > 0) && amount < 100000) { // Deposit& max 1 million USD
            this.balance += amount;
            // Convert amount to positive number for accurate comparison and calculation
        } else if ((amount < 0) && this.balance >= Math.abs(amount)) { // Withdrawal
            this.balance -= Math.abs(amount);
        } else if ((amount < 0) && this.balance < Math.abs(amount)) { // Withdrawal but not enough money
            System.out.println("Cant withdraw money: You only have " + this.balance + " USD in your account.");
        } else {
            System.out.println("Sorry, something went wrong");
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    // E.g. to assign an already created account to a customer
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
