package com.sumit.controller;

import com.sumit.modal.Transaction;
import com.sumit.modal.User;
import com.sumit.service.TransactionService;
import com.sumit.service.UserService;
import com.sumit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;
    @GetMapping("/api/transactions")
    public ResponseEntity<List<Transaction>> getUserTransactionHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Transaction> transactions=transactionService.getUserTransactions(user);
        return new ResponseEntity<>(transactions, HttpStatus.OK);

    }
}
