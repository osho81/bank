package com.yaelev.bank.repository;

import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.TransactionAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface TransactionAccountRepository extends CrudRepository<TransactionAccount, Long> {
    Optional<TransactionAccount> findTransactionAccountByAccountNo(String accountNo);

    // Return a list of associated accounts for passed in customer
    ArrayList<TransactionAccount> findAllByCustomer(Customer customer);

}
