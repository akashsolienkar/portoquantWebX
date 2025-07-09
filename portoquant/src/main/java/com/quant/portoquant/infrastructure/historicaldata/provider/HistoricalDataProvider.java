package com.quant.portoquant.infrastructure.historicaldata.provider;

import java.util.List;

public interface HistoricalDataProvider {
 List<Double> getHistoricalPrices(String symbol);
}
