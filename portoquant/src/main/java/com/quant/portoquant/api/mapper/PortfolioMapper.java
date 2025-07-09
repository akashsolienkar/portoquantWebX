package com.quant.portoquant.api.mapper;

import com.quant.portoquant.api.dto.AssetRequest;
import com.quant.portoquant.api.dto.AssetResponse;
import com.quant.portoquant.api.dto.PortfolioRequest;
import com.quant.portoquant.api.dto.PortfolioResponse;
import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.domain.model.Portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

//    public Portfolio toEntity(PortfolioRequest request) {
//        Portfolio portfolio = new Portfolio();
//        portfolio.setName(request.getName());
//       
//        
//        return portfolio;
//    }
//
//    public PortfolioResponse toResponse(Portfolio portfolio) {
//        PortfolioResponse response = new PortfolioResponse();
//        response.setId(portfolio.getId());
//        
//        response.setName(portfolio.getName());      
//        response.setTotalValuation(portfolio.getTotalValutaion());
//        // You can map assets separately
//        return response;
//    }
	
	public static Portfolio toEntity(PortfolioRequest request) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.getName());
        portfolio.setTotalValuation(request.getTotalValuation());
        
//        mapAssetRequest(request.getAssetIds());
        
        portfolio.setAssets(mapAssetRequest(request.getAssetIds()));
        portfolio.getAssets().stream().forEach(asset->asset.setPortfolio(portfolio));
        // owner will be set in service using ownerId
        return portfolio;
    }

    public static PortfolioResponse toResponse(Portfolio portfolio) {
        PortfolioResponse response = new PortfolioResponse();
        response.setId(portfolio.getId());
        response.setName(portfolio.getName());
        response.setTotalValuation(portfolio.getTotalValuation());
        response.setOwnerId(portfolio.getOwner().getId());
        List<AssetResponse> assetResponses = new ArrayList<AssetResponse> ();
        for(Asset asset: portfolio.getAssets())
        	assetResponses.add(AssetMapper.toResponse(asset));
        	
        response.setAssets(assetResponses);
        
        return response;
    }
    
    public static List<Asset> mapAssetRequest(List<AssetRequest> assetRequest)
    {
    List<Asset> assets = assetRequest != null
            ? assetRequest.stream()
                .map(request -> AssetMapper.toAssetEntity(request))
                .collect(Collectors.toList())
            : Collections.emptyList();
    
    return assets;
    }
    
    
}
