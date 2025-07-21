package com.quant.portoquant.application.service;


import java.util.List;

import com.quant.portoquant.domain.model.Asset;
import com.quant.portoquant.infrastructure.historicaldata.models.HistoricalPrice;

public interface HistoricalDataLoaderService {

    void runRollingUpdateForExistingTickers();

    List<HistoricalPrice> ensureTickerPresentOrInsert(Asset asset);
}
