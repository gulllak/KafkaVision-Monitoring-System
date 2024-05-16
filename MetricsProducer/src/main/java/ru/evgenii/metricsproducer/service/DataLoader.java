package ru.evgenii.metricsproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.evgenii.RequestHitDto;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoader {
    private final KafkaTemplate<String, RequestHitDto> kafkaTemplate;
    private static final String[] APPS = {"App1", "App2", "App3"};
    private static final String[] URIS = {"/booking/book/1", "/login", "/register/new"};
    private static final Random RANDOM = new Random();

    public void run() {
        log.info("Начинаем отпралвять тестовые данные.");
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("metrics-topic", generateRandomRequestHit());
        }
        log.info("Тестовые данные отправлены.");
    }

    private RequestHitDto generateRandomRequestHit() {
        RequestHitDto requestHitDto = new RequestHitDto();
        requestHitDto.setApp(APPS[RANDOM.nextInt(APPS.length)]);
        requestHitDto.setUri(URIS[RANDOM.nextInt(URIS.length)]);
        requestHitDto.setIp(generateRandomIp());
        requestHitDto.setTimestamp(LocalDateTime.now());
        return requestHitDto;
    }

    private String generateRandomIp() {
        return RANDOM.nextInt(256) + "." + RANDOM.nextInt(256) + "." + RANDOM.nextInt(256) + "." + RANDOM.nextInt(256);
    }
}
