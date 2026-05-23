package com.sumit.service;

import com.sumit.modal.Asset;
import com.sumit.modal.Coin;
import com.sumit.modal.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin, Double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getUserByAssetId(Long userId);
    List<Asset> getUserAssets(Long userId);
    Asset updateAsset(Long assetId,Double quantity) throws Exception;
    Asset findAssetByCoinIdAndUserId(Long userId,String coinId);
    void deleteAsset(Long assetId);

}
