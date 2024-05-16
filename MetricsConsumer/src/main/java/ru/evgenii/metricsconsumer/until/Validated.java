package ru.evgenii.metricsconsumer.until;

import ru.evgenii.RequestHitDto;

public class Validated {

    public static void valid(RequestHitDto requestHitDto) {
        if (requestHitDto.getApp() == null || requestHitDto.getApp().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (requestHitDto.getUri() == null || requestHitDto.getUri().isEmpty()) {
            throw new IllegalArgumentException("URI не может быть пустым");
        }
        if (requestHitDto.getIp() == null || requestHitDto.getIp().isEmpty()) {
            throw new IllegalArgumentException("IP не может быть пустым");
        }
        if (requestHitDto.getTimestamp() == null) {
            throw new IllegalArgumentException("Время отсутствует");
        }
    }
}
