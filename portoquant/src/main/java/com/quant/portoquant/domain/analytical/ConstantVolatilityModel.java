package com.quant.portoquant.domain.analytical;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Implements constant volatility over time.
 */
@Data
@AllArgsConstructor
public class ConstantVolatilityModel implements VolatilityModel {
    private double volatility;

    @Override
    public double getVolatility(int time) {
        return volatility;
    }
}
