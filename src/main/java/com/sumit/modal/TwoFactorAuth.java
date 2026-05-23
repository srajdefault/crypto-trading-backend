package com.sumit.modal;

import com.sumit.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean Enabled=false;
    private VerificationType sendTo;



}
