package org.soloactive.tick;

import org.soloactive.core.aggregate.AggregateRoot;
import org.soloactive.core.event.Event;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static List<Event> getEvents(AggregateRoot root) {
        List<Event> events = new ArrayList<>();
        for (Event evt : root.getUncommittedChanges()) {
                events.add(evt);
        }
        return events;
    }

}
