package com.quant.portoquant.domain.calculations.returnestimations;

import com.quant.portoquant.domain.analytical.ExpectedReturnModel;
import com.quant.portoquant.domain.model.Asset;

public interface ExpectedReturnCalculator {
    ExpectedReturnModel calculate(Asset asset);
    boolean support(Asset asset);
}

