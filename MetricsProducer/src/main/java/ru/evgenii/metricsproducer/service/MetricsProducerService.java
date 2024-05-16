package ru.evgenii.metricsproducer.service;

import ru.evgenii.RequestHitDto;

public interface MetricsProducerService {
    void send(RequestHitDto requestHitDto);
}
