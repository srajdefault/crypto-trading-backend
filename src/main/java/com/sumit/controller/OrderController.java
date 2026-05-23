package com.sumit.controller;

import com.sumit.domain.OrderType;
import com.sumit.modal.Coin;
import com.sumit.modal.Order;
import com.sumit.modal.User;
import com.sumit.request.CreateOrderRequest;
import com.sumit.service.CoinService;
import com.sumit.service.OrderService;
import com.sumit.service.UserService;
import com.sumit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,@RequestBody
    CreateOrderRequest req) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(req.getCoinId());
        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByid(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception{

        User user = userService.findUserProfileByJwt(jwtToken);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }
        else{
            throw new Exception("you dont have access");
        }
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required=false) OrderType order_type,
            @RequestParam(required=false) String asset_symbol
    ) throws Exception{

        Long userId=userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders=orderService.getAllOrdersOfuser(userId,order_type,asset_symbol);
        return ResponseEntity.ok(userOrders);
    }
}
