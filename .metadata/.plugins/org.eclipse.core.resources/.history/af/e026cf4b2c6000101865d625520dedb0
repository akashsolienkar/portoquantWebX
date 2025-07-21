package com.quant.portoquant.application.service.impl;



import com.quant.portoquant.api.dto.SimulationResponse;
import com.quant.portoquant.api.dto.SimulationStatusResponse;
import com.quant.portoquant.api.mapper.SimulationMapper;
import com.quant.portoquant.application.service.SimulationService;
import com.quant.portoquant.domain.model.Portfolio;
import com.quant.portoquant.domain.model.SimulationResult;
import com.quant.portoquant.domain.model.enums.SimulationStatus;
import com.quant.portoquant.domain.simulation.executors.MonteCarloSimulationExecutor;
import com.quant.portoquant.infrastructure.exception.ResourceNotFoundException;
import com.quant.portoquant.infrastructure.repository.PortfolioRepository;
import com.quant.portoquant.infrastructure.repository.SimulationResultRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulationServiceImpl implements SimulationService{

    private final PortfolioRepository portfolioRepository;
    private final SimulationResultRepository simulationResultRepository;
    private final MonteCarloSimulationExecutor executor;

    
    @Override
    @Transactional
    public SimulationResponse runSimulation(UUID portofolioId, int numOfSimulations) {
        UUID portfolioId = portofolioId;
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        SimulationResult result = new SimulationResult();
        result.setPortfolio(portfolio);
        result.setNumSimulations(numOfSimulations);
        result.setCreatedAt(LocalDateTime.now());
        result.setStatus(SimulationStatus.RUNNING);
        SimulationResult saved = simulationResultRepository.save(result);
        simulationResultRepository.flush();
        try {
     
            // Perform simulation (this will update risk metrics internally)
           SimulationResult s= executor.runSimulations(portfolio,numOfSimulations);   
   
           saved.setFinalPortfolioValue(s.getFinalPortfolioValue()); 
           saved.setValueAtRisk(s.getValueAtRisk());          
           saved.setExpectedShortfall(s.getExpectedShortfall());     
           saved.setVolatilityPercent(s.getVolatilityPercent());     
           saved.setBankruptcyProbability(s.getBankruptcyProbability());   
                     
     
            saved.setStatus(SimulationStatus.SUCCESSFUL);
        } catch (Exception e)
        {
        	saved.setStatus(SimulationStatus.FAILED);
            throw new RuntimeException("Simulation failed", e);
        }

        saved.setCompletedAt(LocalDateTime.now());
//        SimulationResult saved = simulationResultRepository.save(result);
        return SimulationMapper.toResponse(saved);
    }

    @Override
    public SimulationResponse getSimulationById(Long simulationId) {
        SimulationResult result = simulationResultRepository.findById(simulationId)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found"));
        return SimulationMapper.toResponse(result);
    }

    @Override
    public List<SimulationResponse> getSimulationsForPortfolio(UUID portfolioId) {
        List<SimulationResult> results = simulationResultRepository.findByPortfolioId(portfolioId);
        return results.stream()
                .map(SimulationMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SimulationStatusResponse> getRunningSimulations() {
        return simulationResultRepository.findByStatus(SimulationStatus.RUNNING)
                .stream()
                .map(result -> SimulationStatusResponse.builder()
                        .simulationId(result.getId())
                        .portfolioId(result.getPortfolio().getId())
                        .status(result.getStatus().name())
                        .startedAt(result.getCreatedAt())
                        .build())
                .toList();
    }
}
