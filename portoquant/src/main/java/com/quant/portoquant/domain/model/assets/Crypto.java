package com.quant.portoquant.domain.model.assets;

import com.quant.portoquant.domain.model.Asset;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CRYPTO")
public class Crypto extends Asset {
}
