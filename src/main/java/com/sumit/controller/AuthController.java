package com.sumit.controller;

import com.sumit.config.JwtProvider;
import com.sumit.modal.TwoFactorOtp;
import com.sumit.modal.User;
import com.sumit.repository.UserRepository;
import com.sumit.response.AuthResponse;
import com.sumit.service.CustomUserDetailsService;
import com.sumit.service.EmailService;
import com.sumit.service.TwoFactorOtpService;
import com.sumit.service.WatchListService;
import com.sumit.utils.OtpUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private EmailService emailService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        String userName = user.getEmail();
        User isEmail=userRepository.findByEmail(user.getEmail());
        if(isEmail!=null){
            throw new Exception("email is already used with another account");
        }
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        User savedUser=userRepository.save(newUser);
        watchListService.createWatchList(savedUser);
        Authentication auth=new UsernamePasswordAuthenticationToken
                (
                        user.getEmail(),
                        user.getPassword()
                );
        String jwt= JwtProvider.generateToken(auth);
        User authuser=userRepository.findByEmail(userName);
        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res=new AuthResponse();
            res.setMessage("Two-Factor Authentication Success");
            res.setTwoFactorAuthEnabled(true);
            String otp= OtpUtils.generateOtp();
            TwoFactorOtp oldOtp=twoFactorOtpService.findByUser(authuser.getId());
            if(oldOtp!=null){
                twoFactorOtpService.deleteTwoFactorOtp(oldOtp);
            }

            TwoFactorOtp newOtp=twoFactorOtpService.createTwoFactorOtp(authuser,otp,jwt);
            emailService.sendVerificatonOtpEmail(userName,otp);
            res.setSession(newOtp.getId());
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String userName=user.getEmail();
        String password=user.getPassword();



        Authentication auth=authenticate(userName,password);
        String jwt= JwtProvider.generateToken(auth);
        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
        if(userDetails==null){
            throw new BadCredentialsException("invalid username or password");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,@RequestParam String id) throws Exception {
        TwoFactorOtp twoFactorOtp=twoFactorOtpService.findById(otp);
        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp,otp)){
            AuthResponse res=new AuthResponse();
            res.setMessage("two factor authentication verified");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOtp.getJwt());
            return new  ResponseEntity<>(res,HttpStatus.OK);


        }
        throw new Exception("invalid otp");

    }
}
