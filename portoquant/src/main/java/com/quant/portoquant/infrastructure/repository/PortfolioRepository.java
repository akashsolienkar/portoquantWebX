package com.quant.portoquant.infrastructure.repository;

import com.quant.portoquant.domain.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    
	List<Portfolio> findAllByOwnerId(UUID userId);
}
