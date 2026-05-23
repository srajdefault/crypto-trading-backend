package com.sumit.service;

import com.sumit.modal.Coin;
import com.sumit.modal.User;
import com.sumit.modal.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;
    Coin addItemToWatchList(Coin coin , User user) throws Exception;

}
