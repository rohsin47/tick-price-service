package org.soloactive.core.event.store;

import org.soloactive.core.aggregate.AggregateRoot;
import org.soloactive.core.event.Event;
import org.soloactive.core.event.EventPublisher;
import org.soloactive.core.exception.OptimisticLockingException;
import org.soloactive.tick.domain.Tick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEventStore implements EventStore {

    private final Map<UUID, List<Event>> events = new ConcurrentHashMap<>();
    private final EventPublisher publisher;

    public InMemoryEventStore(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void save(UUID aggregateId, Iterable<Event> newEvents, int expectedVersion) throws OptimisticLockingException {
        List<Event> existingEvents;
        int currentVersion = 0;
        if (events.containsKey(aggregateId)) {
            existingEvents = events.get(aggregateId);
            currentVersion = existingEvents.get(existingEvents.size() - 1).version;
        } else {
            existingEvents = new ArrayList<>();
            events.put(aggregateId, existingEvents);
        }
        if (expectedVersion != currentVersion)
            throw new OptimisticLockingException(String.format("Expected version %d does not match current stored version %d", expectedVersion, currentVersion));

        for (Event e : newEvents) {
            existingEvents.add(e);
            publisher.publish(aggregateId, e);
        }
    }

    @Override
    public List<Event> load(UUID aggregateId) {
        List<Event> aggregateEvents = events.getOrDefault(aggregateId, new ArrayList<>());
        return new ArrayList<>(aggregateEvents);
    }

    @Override
    public AggregateRoot getById(UUID aggregateId) {
        List<Event> events  = load(aggregateId);
        Tick tick = new Tick(aggregateId);
        tick.loadFromHistory(events);
        return tick;
    }
}
