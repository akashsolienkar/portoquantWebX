package com.quant.portoquant.domain.analytical;

/**
 * Strategy interface for modeling expected return of an asset.
 */
@FunctionalInterface
public interface ExpectedReturnModel {
    double getExpectedReturn(int time); // time in years
}
