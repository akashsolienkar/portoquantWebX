package com.quant.portoquant.api.controller;




import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quant.portoquant.api.dto.SimulationStatusResponse;
import com.quant.portoquant.application.service.SimulationService;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final SimulationService simulationService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/simulations/status")
    public ResponseEntity<List<SimulationStatusResponse>> getSimulationStatus(@PathVariable String SimulationStatus) {
        return ResponseEntity.ok(simulationService.getRunningSimulations());
    }
}
