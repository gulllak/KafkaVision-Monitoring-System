package ru.evgenii.metricsproducer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.evgenii.RequestHitDto;
import ru.evgenii.metricsproducer.service.MetricsProducerService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsProducerServiceImpl implements MetricsProducerService {
    private final KafkaTemplate<String, RequestHitDto> kafkaTemplate;

    @Override
    public void send(RequestHitDto requestHitDto) {
        try {
        log.info("Сообщение {} отправлено в топик {}", requestHitDto, "metrics-topic");
        kafkaTemplate.send("metrics-topic", requestHitDto);
        } catch (Exception e) {
            log.error("Ошибка отправки в Kafka", e);
        }
    }
}
