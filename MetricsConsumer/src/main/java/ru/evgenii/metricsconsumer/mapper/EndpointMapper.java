package ru.evgenii.metricsconsumer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.evgenii.RequestHitDto;
import ru.evgenii.metricsconsumer.dto.MetricDto;
import ru.evgenii.metricsconsumer.dto.EndpointsHitsDto;
import ru.evgenii.metricsconsumer.entity.Endpoint;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndpointMapper {
    @Mapping(target = "id", ignore = true)
    Endpoint toEndpoint(RequestHitDto requestHitDto);

    EndpointsHitsDto toResponseDto(String app, String uri, Long hits);

    MetricDto ResponseViewStatsDto(Endpoint endpoint);
}
