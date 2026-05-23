package com.sumit.controller;

import com.sumit.modal.Coin;
import com.sumit.modal.User;
import com.sumit.modal.WatchList;
import com.sumit.repository.UserRepository;
import com.sumit.repository.WatchListRepository;
import com.sumit.service.CoinService;
import com.sumit.service.UserService;
import com.sumit.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader ("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception{
        WatchList watchList = watchListService.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }
    @PatchMapping("add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@PathVariable String coinId, @RequestHeader ("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin= coinService.findById(coinId);
        Coin addedCoin=watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);
    }



}
