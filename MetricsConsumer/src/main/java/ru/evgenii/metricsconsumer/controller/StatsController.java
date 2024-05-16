package ru.evgenii.metricsconsumer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.evgenii.metricsconsumer.dto.MetricDto;
import ru.evgenii.metricsconsumer.dto.EndpointsHitsDto;
import ru.evgenii.metricsconsumer.service.StatsService;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/hits")
    @ResponseStatus(HttpStatus.OK)
    public List<EndpointsHitsDto> getEndpointsHits(@RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        return statsService.getEndpointsHits(unique);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetricDto getMetricById(@PathVariable(value = "id") long id) {
        return statsService.getById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MetricDto> getMetrics() {
        return statsService.getAll();
    }
}
