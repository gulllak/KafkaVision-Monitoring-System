package ru.evgenii.metricsconsumer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import ru.evgenii.RequestHitDto;
import ru.evgenii.metricsconsumer.entity.Endpoint;
import ru.evgenii.metricsconsumer.mapper.EndpointMapper;
import ru.evgenii.metricsconsumer.repository.StatsRepository;
import ru.evgenii.metricsconsumer.service.MetricsConsumerService;
import ru.evgenii.metricsconsumer.until.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsConsumerServiceImpl implements MetricsConsumerService {
    private  final EndpointMapper mapper;

    private final StatsRepository repository;

    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 1000), dltTopicSuffix = ".DLT")
    @KafkaListener(topics = "metrics-topic", groupId = "metricsGroup")
    public void consume(RequestHitDto requestHitDto) {
        log.info("Сообщение получено {}", requestHitDto.toString());

        try {
            Validated.valid(requestHitDto);
        } catch (IllegalArgumentException ex) {
            log.info("Ошибка во время валидации " + ex);
            throw ex;
        }

        try {
            Endpoint endpoint = mapper.toEndpoint(requestHitDto);
            repository.save(endpoint);
            log.info("Сообщение сохранено в БД");
        } catch (RuntimeException ex) {
            log.info("Ошибка во время попытки получить сообщение " + ex);
            throw ex;
        }
    }

    @DltHandler
    public void dlt(RequestHitDto requestHitDto) {
        log.info("Сообщение попало в DTL топик {}", requestHitDto.toString());
    }
}
