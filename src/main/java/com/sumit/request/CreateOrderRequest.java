package com.sumit.request;

import com.sumit.domain.OrderType;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
