 package com.quant.portoquant.infrastructure.historicaldata.models;

import java.util.List;
import java.util.UUID;

import com.quant.portoquant.domain.model.enums.AssetType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalDataMeta
{

	@Id
    private UUID id;
	
	@Enumerated(EnumType.STRING)
	AssetType assetType;
	
	private String name;
	
	int frequency;
	
	@OneToMany(mappedBy="metaData", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HistoricalPrice> historicalPrices;
	
}
