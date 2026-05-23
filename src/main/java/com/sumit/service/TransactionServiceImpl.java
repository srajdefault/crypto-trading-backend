package com.sumit.service;

import com.sumit.domain.TransactionType;
import com.sumit.modal.Coin;
import com.sumit.modal.Transaction;
import com.sumit.modal.User;
import com.sumit.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(User user, TransactionType transactionType, Coin coin, Double amount) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCoin(coin);
        transaction.setType(transactionType);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserId(user.getId());
    }
}
