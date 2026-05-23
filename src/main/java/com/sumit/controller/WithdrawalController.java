package com.sumit.controller;

import com.sumit.modal.User;
import com.sumit.modal.Wallet;
import com.sumit.modal.WalletTransaction;
import com.sumit.modal.Withdrawal;
import com.sumit.service.UserService;
import com.sumit.service.WalletService;
import com.sumit.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withDrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Wallet userWallet=walletService.getUserWallet(user);
        Withdrawal withdrawal=withdrawalService.requestWithDrawwal(amount,user);
        walletService.addBalance(userWallet,-withdrawal.getAmount());
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithDrawal(@PathVariable Long id,
                                               @PathVariable boolean accept,
                                               @RequestHeader("Authprization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal=withdrawalService.proceedWithWithDrawwal(id,accept);
        Wallet wallet = walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(wallet,withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawal=withdrawalService.getUserWithdrawalsHistory(user);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawal=withdrawalService.getAllWithDrawalRequest();
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }


}
