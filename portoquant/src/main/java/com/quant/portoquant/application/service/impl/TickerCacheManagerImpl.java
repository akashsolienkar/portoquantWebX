package com.quant.portoquant.application.service.impl;


import com.quant.portoquant.application.service.RedisCacheService;
import com.quant.portoquant.application.service.TickerCacheManager;
import com.quant.portoquant.infrastructure.cache.rediscache.RedisEvictionPolicyManager;
import com.quant.portoquant.infrastructure.cache.rediscache.RedisTickerCacheLoader;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalDataMeta;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalPrice;

import com.quant.portoquant.infrastructure.repository.HistoricalDataMetaRepository;
import com.quant.portoquant.infrastructure.repository.HistoricalPriceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

@Component
@RequiredArgsConstructor
public class TickerCacheManagerImpl implements TickerCacheManager {

    private final RedisCacheService redisCacheService;
    private final RedisEvictionPolicyManager evictionManager;
    private final HistoricalDataMetaRepository metaRepo;
    private final HistoricalPriceRepository priceRepo;

    @Override
    public List<HistoricalPrice> getTickerPrices(String ticker) {
    	  System.out.println("size of prices 1");
        List<HistoricalPrice> cached = redisCacheService.getCachedTicker(ticker);
        
        if (cached != null) {
            redisCacheService.incrementFrequency(ticker);
            System.out.println("cached size - "+cached.size());
            return cached;
        }
        System.out.println("size of prices 2");
        HistoricalDataMeta meta = metaRepo.findByName(ticker);
        if (meta!=null) {
        	
            List<HistoricalPrice> prices = priceRepo.findTopNByMetaDataOrderByTradingIndexDesc(meta.getId(), PageRequest.of(0, 257));
            System.out.println("size of prices -----"+prices.size());
            int dbFreq = meta.getFrequency();

            if (redisCacheService.isFull()) {
                evictionManager.evictIfNeeded(dbFreq);
            }

            redisCacheService.cacheTicker(ticker, prices, dbFreq);
            return prices;
        }

        return List.of(); // or throw an exception
    }

    @Override
    public void preloadTopTickersFromDB() {
        List<HistoricalDataMeta> topTickers = metaRepo.findTopNTickersByFrequency(PageRequest.of(0, redisCacheService.MAX_CACHE_SIZE)); // N = MAX_CACHE_SIZE
        for (HistoricalDataMeta meta : topTickers) {
            List<HistoricalPrice> prices = priceRepo.findTopNByMetaDataOrderByTradingIndexDesc(meta.getId(), PageRequest.of(0, 257));
        
            redisCacheService.cacheTicker(meta.getName(), prices, meta.getFrequency());
        }
    }

    @Override
    public void loadToCache(HistoricalDataMeta meta, List<HistoricalPrice> prices, int frequency) {
        if (redisCacheService.isFull()) {
            evictionManager.evictIfNeeded(frequency);
        }
        redisCacheService.cacheTicker(meta.getName(), prices, frequency);
    }
   
    @Override
    public void clearCache()
    {
    	redisCacheService.flushAll();
    }

	@Override
	public void syncFreqFromRedisToDB()
	{
		
		
		    Map<String, Integer> redisFreqs = redisCacheService.getAllFrequencies();

		    for (Entry<String, Integer> entry : redisFreqs.entrySet())
		    {
		    	metaRepo.updateFrequencyByName(entry.getKey(), entry.getValue());
		    }
		    
		    metaRepo.updateFrequenciesWithRank();
		
	}
    
    
    
    
//    @Override
//    @Transactional
//    public void saveFrequencyToDB(String ticker, int frequency) {
//        HistoricalDataMeta meta = metaRepo.findByName(ticker);
//        if (meta!=null) 
//        {
//        	 meta.setFrequency(frequency);
//             metaRepo.save(meta);
//        }
//    }
}
