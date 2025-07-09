package com.quant.portoquant.domain.analytical;

import java.util.List;
import lombok.AllArgsConstructor;

/**
 * Uses a time-based function to dynamically compute expected return.
 */
@AllArgsConstructor
public class TimeVaryingExpectedReturnModel implements ExpectedReturnModel {
   
	private List<Double> expectedReturn;

    @Override
    public double getExpectedReturn(int time) {
        return time>=expectedReturn.size()?expectedReturn.get(expectedReturn.size()): expectedReturn.get(time);
    }
}
