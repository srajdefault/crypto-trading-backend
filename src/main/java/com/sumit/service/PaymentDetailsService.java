package com.sumit.service;

import com.sumit.modal.PaymentDetails;
import com.sumit.modal.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,String ifsc,String accountHolderName,String bankName,User user);
    public PaymentDetails getUserPaymentDetails(User user);


}
