package com.sumit.service;

import com.sumit.modal.TwoFactorOtp;
import com.sumit.modal.User;



public interface TwoFactorOtpService {
       TwoFactorOtp createTwoFactorOtp(User user, String otp,String jwt);
       TwoFactorOtp findByUser(Long userId);
       TwoFactorOtp findById(String id);
       boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp,String otp);
       void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp);
}
