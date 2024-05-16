package ru.evgenii.metricsconsumer.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
        log.info("EntityNotFoundException: {}", message);
    }
}
