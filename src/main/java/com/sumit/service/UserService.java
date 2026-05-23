package com.sumit.service;

import com.sumit.domain.VerificationType;
import com.sumit.modal.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findByEmail(String email) throws Exception;
    public User findUserById(Long id) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificatontype, String sendTo, User user);
    User updatePassword(User user,String newPassword);

}
