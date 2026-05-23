package com.sumit.service;

import com.sumit.modal.User;
import com.sumit.modal.Withdrawal;
import lombok.With;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithDrawwal(Long amount, User user);
    Withdrawal proceedWithWithDrawwal(Long withDrawalId,boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalsHistory(User user);
    List<Withdrawal> getAllWithDrawalRequest();

}
