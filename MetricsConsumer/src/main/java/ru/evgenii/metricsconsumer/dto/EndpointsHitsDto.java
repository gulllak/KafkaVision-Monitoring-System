package ru.evgenii.metricsconsumer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndpointsHitsDto {
    private String app;
    private String uri;
    private Long hits;
}
