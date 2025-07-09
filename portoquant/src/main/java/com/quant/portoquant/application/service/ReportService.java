package com.quant.portoquant.application.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<byte[]> generatePortfolioReport(UUID portfolioId);
}
