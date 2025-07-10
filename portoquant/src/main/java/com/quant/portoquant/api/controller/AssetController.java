package com.quant.portoquant.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quant.portoquant.api.dto.AssetRequest;
import com.quant.portoquant.api.dto.AssetResponse;
import com.quant.portoquant.application.service.AssetService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetResponse> addAsset(
            @PathVariable UUID portfolioId,
            @RequestBody AssetRequest request) {

        return ResponseEntity.ok(assetService.addAssetToPortfolio(portfolioId, request));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAssets(@PathVariable UUID portfolioId) {
        return ResponseEntity.ok(assetService.getAssetsByPortfolioId(portfolioId));
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<AssetResponse> getAsset(
            @PathVariable UUID portfolioId,
            @PathVariable UUID assetId) {
        return ResponseEntity.ok(assetService.getAssetById(portfolioId, assetId));
    }

    @PutMapping("/{assetId}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable UUID portfolioId,
            @PathVariable UUID assetId,
            @RequestBody AssetRequest request) {
        return ResponseEntity.ok(assetService.updateAsset(portfolioId, assetId, request));
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<Void> deleteAsset(
            @PathVariable UUID portfolioId,
            @PathVariable UUID assetId) {
        assetService.deleteAsset(portfolioId, assetId);
        return ResponseEntity.noContent().build();
    }
}
