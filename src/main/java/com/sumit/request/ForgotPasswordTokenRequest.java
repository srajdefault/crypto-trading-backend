package com.sumit.request;

import com.sumit.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest  {
    private String sendTo;
    private VerificationType verificationType;
}
