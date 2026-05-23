package com.sumit.modal;

import com.sumit.domain.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;
    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime timeStamp=LocalDateTime.now();
    private Double amount;
    @ManyToOne
    private Coin coin;
    private String status="SUCCESS";

}
