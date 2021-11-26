package org.soloactive.tick.service;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.command.Command;
import org.soloactive.tick.command.DefaultCommandDispatcher;
import org.soloactive.tick.domain.TickStatistic;
import org.soloactive.tick.event.TickPostedEvent;
import org.soloactive.tick.repository.TickRepository;
import org.soloactive.tick.util.DateUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TickService {

    DefaultCommandDispatcher commandDispatcher;
    TickRepository tickRepository;

    public TickService(DefaultCommandDispatcher commandDispatcher, TickRepository repository) {
        this.commandDispatcher = commandDispatcher;
        this.tickRepository = repository;
    }

    public void post(Command command) {
        commandDispatcher.dispatch(command);
    }

    public TickStatistic calculateTickStats() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        List<Double> prices = tickRepository.getEventsList().values().stream()
                .filter(event -> DateUtils.isValid(currentTime, event.getCreationTime()))
                .map(TickPostedEvent::getPrice)
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList());
        return TickStatistic.builder()
                .count(prices.size())
                .avg(prices.stream().mapToDouble(Double::valueOf).average().orElse(0.0))
                .max(prices.stream().mapToDouble(Double::valueOf).max().orElse(0.0))
                .min(prices.stream().mapToDouble(Double::valueOf).min().orElse(0.0))
                .build();
    }

    public TickStatistic calculateTickStatsByInstrument(String instrument) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        List<Double> prices = tickRepository.getEventsList().values().stream()
                .filter(event -> event.getInstrument().equals(instrument))
                .filter(event -> DateUtils.isValid(currentTime, event.getCreationTime()))
                .map(TickPostedEvent::getPrice)
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList());
        return TickStatistic.builder()
                .count(prices.size())
                .avg(prices.stream().mapToDouble(Double::valueOf).average().orElse(0.0))
                .max(prices.stream().mapToDouble(Double::valueOf).max().orElse(0.0))
                .min(prices.stream().mapToDouble(Double::valueOf).min().orElse(0.0))
                .build();
    }

}
