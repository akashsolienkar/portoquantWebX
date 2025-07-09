package com.quant.portoquant.infrastructure.reports.executor;


public interface ReportExecutor<T> {
    byte[] execute(T reportData);
}
