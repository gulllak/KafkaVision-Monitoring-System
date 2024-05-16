package ru.evgenii.metricsproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.evgenii.RequestHitDto;
import ru.evgenii.metricsproducer.service.DataLoader;
import ru.evgenii.metricsproducer.service.MetricsProducerService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MetricsController {
    private final MetricsProducerService metricsProducerService;

    private final DataLoader dataLoader;

    @PostMapping("/metrics")
    public ResponseEntity<String> sendMetrics(@RequestBody RequestHitDto requestHitDto) {
        metricsProducerService.send(requestHitDto);
        return ResponseEntity.ok("Json сообщение отправлено в топик Kafka");
    }

    @GetMapping("/metrics")
    public ResponseEntity<String> testData() {
        dataLoader.run();
        return ResponseEntity.ok("Тестовые данные отправлены");
    }
}
