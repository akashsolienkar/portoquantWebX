package com.quant.portoquant.infrastructure.scheduler;
import com.quant.portoquant.application.service.HistoricalDataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class HistoricalDataSyncScheduler {

    
    private final HistoricalDataLoaderService loaderService;

    /**
     * Runs every weekday at 5:30 PM IST (after market close).
     * Cron format: second, minute, hour, day of month, month, day of week
     */
    @Scheduled(cron = "0 30 17 * * MON-FRI", zone = "Asia/Kolkata")
    public void syncAllHistoricalPrices() {
        log.info(" Starting daily historical data sync job...");
                
        loaderService.runRollingUpdateForExistingTickers();
      
        log.info(" Historical data sync job completed.");
    }
}
