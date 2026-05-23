package com.sumit.service;

import com.sumit.domain.WithdrawalStatus;
import com.sumit.modal.User;
import com.sumit.modal.Withdrawal;
import com.sumit.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class WithDrawalServiceImpl implements WithdrawalService{
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Override
    public Withdrawal requestWithDrawwal(Long amount, User user) {
        Withdrawal withDrawal=new Withdrawal();
        withDrawal.setAmount(amount);
        withDrawal.setUser(user);
        withDrawal.setWithDrawalStatus(WithdrawalStatus.PENDING);
        return withdrawalRepository.save(withDrawal);
    }

    @Override
    public Withdrawal proceedWithWithDrawwal(Long withDrawalId, boolean accept) throws Exception {

        Optional<Withdrawal> withDrawal=withdrawalRepository.findById(withDrawalId);
        if(withDrawal.isEmpty()){
            throw new Exception("withdrawal not found");

        }
        Withdrawal withdrawal1=withDrawal.get();
        withdrawal1.setDate(LocalDateTime.now());
        if(accept){
            withdrawal1.setWithDrawalStatus(WithdrawalStatus.SUCCESS);
        }
        else{
            withdrawal1.setWithDrawalStatus(WithdrawalStatus.PENDING);
        }


        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalsHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithDrawalRequest() {
        return withdrawalRepository.findAll();
    }
}
