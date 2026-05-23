package com.sumit.controller;

import com.sumit.modal.Asset;
import com.sumit.modal.User;
import com.sumit.service.AssetService;
import com.sumit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<?> getAsset(@PathVariable("assetId") Long assetId) throws Exception {
        Asset asset=assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByCoinIdAndUserId(@PathVariable("coinId") String coinId,
                                                           @RequestHeader("Authorization") String jwt
                                                           ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Asset asset=assetService.findAssetByCoinIdAndUserId(user.getId(),coinId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping()
    public ResponseEntity <List<Asset>> getAssetByCoinIdAndUserId(@RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Asset> asset=assetService.getUserAssets(user.getId());
        return ResponseEntity.ok().body(asset);
    }

}
