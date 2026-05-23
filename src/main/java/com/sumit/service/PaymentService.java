package com.sumit.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.sumit.domain.PaymentMethod;
import com.sumit.modal.PaymentOrder;
import com.sumit.modal.User;
import com.sumit.response.PaymentResponse;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user , Long amount,Long orderId) throws RazorpayException;
    PaymentResponse createStripePaymentLink(User user , Long amount,Long orderId) throws StripeException;

}
