package com.sumit.repository;

import com.sumit.modal.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp , String> {
    TwoFactorOtp findByUserId(Long userId);
}
