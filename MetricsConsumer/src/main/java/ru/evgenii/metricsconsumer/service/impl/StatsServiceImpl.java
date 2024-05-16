package ru.evgenii.metricsconsumer.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.evgenii.metricsconsumer.dto.EndpointsHitsDto;
import ru.evgenii.metricsconsumer.dto.MetricDto;
import ru.evgenii.metricsconsumer.entity.Endpoint;
import ru.evgenii.metricsconsumer.exception.EntityNotFoundException;
import ru.evgenii.metricsconsumer.mapper.EndpointMapper;
import ru.evgenii.metricsconsumer.repository.StatsRepository;
import ru.evgenii.metricsconsumer.service.StatsService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    private final EndpointMapper mapper;

    @Override
    public List<EndpointsHitsDto> getEndpointsHits(boolean unique) {
        List<Endpoint> endpoints = repository.findAll();

        if (unique) {
            return endpoints.stream()
                    .collect(Collectors.groupingBy(Endpoint::getUri, Collectors.mapping(Endpoint::getIp, Collectors.toSet())))
                    .entrySet().stream()
                    .map(entry -> mapper.toResponseDto(findAppByUri(endpoints, entry.getKey()), entry.getKey(), (long) entry.getValue().size()))
                    .sorted(Comparator.comparingLong(EndpointsHitsDto::getHits).reversed())
                    .collect(Collectors.toList());
        } else {
            return endpoints.stream().collect(Collectors.groupingBy(Endpoint::getUri, Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> mapper.toResponseDto(findAppByUri(endpoints, entry.getKey()), entry.getKey(), entry.getValue()))
                    .sorted(Comparator.comparingLong(EndpointsHitsDto::getHits).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<MetricDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::ResponseViewStatsDto)
                .collect(Collectors.toList());
    }

    @Override
    public MetricDto getById(long id) {
        Endpoint endpoint = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Запись с id=%d не найдена", id)));
        return mapper.ResponseViewStatsDto(endpoint);
    }

    private String findAppByUri(List<Endpoint> endpoints, String uri) {
        return endpoints.stream()
                .filter(endpoint -> endpoint.getUri().equals(uri))
                .map(Endpoint::getApp)
                .findFirst()
                .orElse(null);
    }
}
