package ru.evgenii.metricsconsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.evgenii.metricsconsumer.entity.Endpoint;

@Repository
public interface StatsRepository extends JpaRepository<Endpoint, Long> {
}
