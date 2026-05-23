package com.sumit.modal;

import com.sumit.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private WithdrawalStatus withDrawalStatus;
    private Long amount;
    @ManyToOne
    private User user;
    private LocalDateTime date= LocalDateTime.now();



}
