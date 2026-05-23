package com.sumit.service;

import com.sumit.modal.Coin;
import com.sumit.modal.User;
import com.sumit.modal.WatchList;
import com.sumit.repository.UserRepository;
import com.sumit.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService{
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList=watchListRepository.findByUserId(userId);
        if(watchList==null){
            User user = userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));

            throw new Exception("watchlist not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList=new WatchList();
        watchList.setUser(user);
        return watchListRepository.save(watchList);

    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchList=watchListRepository.findById(id);
        if(watchList.isPresent()){
            return watchList.get();
        }
        throw new Exception("watchList not found");
    }

    @Override
    public Coin addItemToWatchList(Coin coin, User user) throws Exception {
        WatchList watchList = findUserWatchList(user.getId());
        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }
        else watchList.getCoins().add(coin);
        watchListRepository.save(watchList);
        return coin;
    }
}
