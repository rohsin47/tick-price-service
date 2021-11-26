package org.soloactive.core.event.store;

import org.soloactive.core.aggregate.AggregateRoot;
import org.soloactive.core.event.Event;
import org.soloactive.core.exception.OptimisticLockingException;

import java.util.List;
import java.util.UUID;

public interface EventStore {

    AggregateRoot getById(UUID aggregateId);
    List<Event> load(UUID aggregateId);
    void save(UUID aggregateId, Iterable<Event> newEvents, int expectedVersion) throws OptimisticLockingException;
}
