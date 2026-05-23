package com.sumit.controller;

import com.sumit.request.ForgotPasswordTokenRequest;
import com.sumit.domain.VerificationType;
import com.sumit.modal.ForgotPasswordToken;
import com.sumit.modal.User;
import com.sumit.modal.VerificationCode;
import com.sumit.request.ResetPasswordRequest;
import com.sumit.response.ApiResponse;
import com.sumit.response.AuthResponse;
import com.sumit.service.EmailService;
import com.sumit.service.ForgotPasswordService;
import com.sumit.service.UserService;
import com.sumit.service.VerificationCodeService;
import com.sumit.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
      private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader ("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);


    }


    @PostMapping("/api/users/verify/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader ("Authorization") String jwt
            ,@PathVariable VerificationType verificationType)
            throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        if(verificationCode==null){
            verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificatonOtpEmail(user.getEmail(),verificationCode.getOtp());

        }

        return new ResponseEntity<>("verification otp sent successfully", HttpStatus.OK);


    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
                                                              @RequestHeader ("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo=verificationCode.getVerificationType()
                .equals(VerificationType.EMAIL)?
                verificationCode.getEmail():
                verificationCode.getMobile();
        boolean isVerified=verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updateUserService=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new  ResponseEntity<>(updateUserService, HttpStatus.OK);
        }
        throw new Exception("wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
                                                        @RequestBody ForgotPasswordTokenRequest req)throws Exception{
        User user=userService.findByEmail(req.getSendTo());
        String otp= OtpUtils.generateOtp();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId());
        if(token==null){
            token=forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
        }
        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificatonOtpEmail(user.getEmail(),token.getOtp());
        }
        AuthResponse response=new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPasswordOtp(@RequestParam String id,
                                                              @RequestBody ResetPasswordRequest req,
                                                              @RequestHeader ("Authorization") String jwt)throws Exception{
        ForgotPasswordToken forgotPasswordToken =forgotPasswordService.findById(id);
       boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());
       if(isVerified){
           userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
           ApiResponse response=new ApiResponse();
           response.setMessage("Password update send successfully");
           return new ResponseEntity<>(response,HttpStatus.OK);
       }
       throw new Exception("wrong otp");
    }



 }
