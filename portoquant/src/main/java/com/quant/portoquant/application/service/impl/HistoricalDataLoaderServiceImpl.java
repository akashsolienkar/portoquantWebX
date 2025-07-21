package com.quant.portoquant.application.service.impl;



import com.quant.portoquant.application.service.HistoricalDataLoaderService;
import com.quant.portoquant.application.service.TickerCacheManager;
import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalDataMeta;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalPrice;
import com.quant.portoquant.infrastructure.historicaldata.provider.HistoricalDataProvider;
import com.quant.portoquant.infrastructure.historicaldata.provider.HistoricalDataSourceRegistry;
import com.quant.portoquant.infrastructure.historicaldata.rolling.RollingWindowManager;
import com.quant.portoquant.infrastructure.repository.HistoricalDataMetaRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricalDataLoaderServiceImpl implements HistoricalDataLoaderService {

    private final HistoricalDataMetaRepository metaRepo;
    private final HistoricalDataSourceRegistry registry;
    private final RollingWindowManager rollingManager;
	
	private final  TickerCacheManager redisCache;
    

    @Override
    public void runRollingUpdateForExistingTickers() {
    	
        List<HistoricalDataMeta> existingMetas = metaRepo.findAll();

        for (HistoricalDataMeta meta : existingMetas) {
            HistoricalDataProvider provider = registry.getProvider(meta.getAssetType());
            List<Double> newPrices = provider.getHistoricalPrices(meta.getName());
            rollingManager.applyRollingUpdate(meta, newPrices);
        }
    }

    @Override
    public List<HistoricalPrice> ensureTickerPresentOrInsert(Asset asset) {
     
    	List<HistoricalPrice> prices =redisCache.getTickerPrices(asset.getTicker());
    	if (prices != null && !prices.isEmpty())
    		return prices;
    	else {
//    	HistoricalDataMeta meta = metaRepo.findByNameAndAssetType(asset.getTicker(), asset.getType());
//
//        if (meta == null) {
                System.out.println("fetching data from provider");  
            HistoricalDataProvider provider = registry.getProvider(asset.getType());
            List<Double> fullPrices = provider.getHistoricalPrices(asset.getTicker());

            HistoricalDataMeta metaNew = HistoricalDataMeta.builder()
                    .id(UUID.randomUUID())
                    .name(asset.getTicker())
                    .assetType(asset.getType())
                    .frequency(1)
                    .build();

            metaRepo.save(metaNew);
            rollingManager.fullInsert(metaNew, fullPrices);
//            List<HistoricalPrice> newPrices= priceRepo.findByMetaDataOrderByTradingIndex(metaNew);
//            redisCache.loadToCache(metaNew, newPrices, 0);  
            return redisCache.getTickerPrices(asset.getTicker());
        }
//        else
//        	return priceRepo.findByMetaDataOrderByTradingIndex(meta);
    }
}
