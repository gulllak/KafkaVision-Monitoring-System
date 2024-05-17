package ru.evgenii.metricsconsumer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Получение статистики по каждому эндпоинту.",
            description = "Показывает сколько всего раз пользователи " +
                    "воспользовались эндпоинтом. Если установить параметр unique=true, то будут показаны только " +
                    "уникальные вызовы эндпоинта от пользователей.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение", content = @Content(schema = @Schema(implementation = EndpointsHitsDto.class)))
            })
    public List<EndpointsHitsDto> getEndpointsHits(@RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        return statsService.getEndpointsHits(unique);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение списка всех метрик",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение", content = @Content(schema = @Schema(implementation = MetricDto.class))),
            })
    public MetricDto getMetricById(@PathVariable(value = "id") long id) {
        return statsService.getById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получение метрики по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение", content = @Content(schema = @Schema(implementation = MetricDto.class)))
            })
    public List<MetricDto> getMetrics() {
        return statsService.getAll();
    }
}
