package com.example.demo.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CustomMetricsService {

    private final Counter customCounter;

    public CustomMetricsService(MeterRegistry registry) {
        this.customCounter = registry.counter("custom.metric.counter", "type", "custom_metric");
    }

    public void performAction() {
        // Логика метода
        customCounter.increment(); // Увеличиваем счётчик
    }
}
