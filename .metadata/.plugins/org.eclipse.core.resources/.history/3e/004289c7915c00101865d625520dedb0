package com.quant.portoquant.api.controller;

import com.quant.portoquant.application.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<byte[]> generatePortfolioReport(@PathVariable UUID portfolioId) {
        return reportService.generatePortfolioReport(portfolioId);
    }

    // You can add this one later for simulation reports
    /*
    @GetMapping("/simulation/{simulationId}")
    public void generateSimulationReport(@PathVariable Long simulationId, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulation-report.pdf");

        reportService.generateSimulationPdfReport(simulationId, response.getOutputStream());
    }
    */
}
