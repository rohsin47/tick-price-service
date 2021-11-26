package org.soloactive.tick.repository;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.tick.event.TickPostedEvent;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TickRepository {

    private final Map<UUID, TickPostedEvent> eventsListMap = new ConcurrentHashMap<>();

    public void save(TickPostedEvent evt) {
        eventsListMap.put(evt.aggregateId, evt);
    }

    public Map<UUID, TickPostedEvent> getEventsList() {
        return Collections.unmodifiableMap(eventsListMap);
    }

}
