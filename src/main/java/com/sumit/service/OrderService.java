package com.sumit.service;

import com.sumit.domain.OrderType;
import com.sumit.modal.Coin;
import com.sumit.modal.Order;
import com.sumit.modal.OrderItem;
import com.sumit.modal.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrdersOfuser(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
