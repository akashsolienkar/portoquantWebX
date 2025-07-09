package com.quant.portoquant.domain.analytical;

/**
 * Strategy interface for modeling volatility of an asset.
 */
@FunctionalInterface
public interface VolatilityModel {
    double getVolatility(int time); // annualized volatility
}
