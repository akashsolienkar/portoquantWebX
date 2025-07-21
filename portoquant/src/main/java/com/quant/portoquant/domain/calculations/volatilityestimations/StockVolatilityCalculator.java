package com.quant.portoquant.domain.calculations.volatilityestimations;


import com.quant.portoquant.application.service.HistoricalDataLoaderService;
import com.quant.portoquant.domain.analytical.TimeVaryingVolatility;
import com.quant.portoquant.domain.analytical.VolatilityModel;
import com.quant.portoquant.domain.garchmodel.GarchModel;
import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.domain.model.enums.AssetType;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalPrice;


import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StockVolatilityCalculator implements VolatilityCalculator {
    
	
	HistoricalDataLoaderService historicalDataLoader;
	GarchModel garchModel;
	
	@Override
    public VolatilityModel calculate(Asset asset) {
        // Dummy logic - could use GARCH or historical stddev
		
		List<HistoricalPrice> prices=historicalDataLoader.ensureTickerPresentOrInsert(asset);
		List<Double> pricelist =prices.stream().map(h->h.getPrice()).collect(Collectors.toList());
        return new TimeVaryingVolatility(garchModel.runGarch(pricelist)); 
    }
	
	@Override
    public boolean support(Asset asset) {
        return asset.getType() == AssetType.STOCK; 
    }
}
