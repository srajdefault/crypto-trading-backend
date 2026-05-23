package com.sumit.service;

import com.sumit.domain.TransactionType;
import com.sumit.modal.Coin;
import com.sumit.modal.Transaction;
import com.sumit.modal.User;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(User user, TransactionType transactionType, Coin coin, Double amount);
    List<Transaction> getUserTransactions(User user);
}
