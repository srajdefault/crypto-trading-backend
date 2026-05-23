package com.sumit.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.sumit.domain.PaymentMethod;
import com.sumit.modal.PaymentOrder;
import com.sumit.modal.User;
import com.sumit.response.PaymentResponse;
import com.sumit.service.PaymentService;
import com.sumit.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod,
                                                          @PathVariable Long amount,
                                                          @RequestHeader("Authorization") String jwt)
            throws Exception ,RazorpayException, StripeException {
        User user=userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;
        PaymentOrder order=paymentService.createOrder(user,amount,paymentMethod);
        if(paymentMethod==PaymentMethod.RAZORPAY){
            paymentResponse=paymentService.createRazorpayPaymentLink(user,amount, order.getId());
        }
        else{
            paymentResponse=paymentService.createStripePaymentLink(user,amount,order.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
