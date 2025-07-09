package com.quant.portoquant.api.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class PortfolioRequest {
	private UUID ownerId;
    private String name;
    private double totalValuation;
    private List<AssetRequest> assetIds; // assuming assets are added separately
}
