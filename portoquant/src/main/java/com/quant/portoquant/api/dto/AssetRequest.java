package com.quant.portoquant.api.dto;

import com.quant.portoquant.domain.model.enums.AssetType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetRequest {
    private String name;
    private String ticker;
    private AssetType type;
    private BigDecimal allocation;
    private int price;
}
