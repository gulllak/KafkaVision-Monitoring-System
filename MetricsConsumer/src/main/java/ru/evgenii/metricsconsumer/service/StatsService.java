package ru.evgenii.metricsconsumer.service;

import ru.evgenii.metricsconsumer.dto.MetricDto;
import ru.evgenii.metricsconsumer.dto.EndpointsHitsDto;

import java.util.List;

public interface StatsService {
    List<EndpointsHitsDto> getEndpointsHits(boolean unique);

    List<MetricDto> getAll();

    MetricDto getById(long id);
}
