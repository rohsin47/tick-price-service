package org.soloactive.tick.event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.soloactive.core.event.Event;
import org.soloactive.core.event.store.EventStore;
import org.soloactive.core.event.store.InMemoryEventStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DefaultEventPublisherTest {

    @Mock
    DefaultEventPublisher publisher;

    @Test
    public void saveEventsForNewAggregate() {
        EventStore eventStore = new InMemoryEventStore(publisher);
        UUID aggregateId = UUID.randomUUID();
        List<Event> newEvents = new ArrayList<>();

        TickPostedEvent evt1 = TickPostedEvent.create(aggregateId, "first name", new BigDecimal("9.0"), 145686655);
        evt1.version = 1;

        TickPostedEvent evt2 = TickPostedEvent.create(aggregateId, "second name", new BigDecimal("9.0"), 145686655);
        evt2.version = 2;

        newEvents.add(evt1);
        newEvents.add(evt2);

        eventStore.save(aggregateId, newEvents, 0);
        List<? extends Event> savedEvents = eventStore.load(aggregateId);

        assertEquals(2, savedEvents.size());
        assertEquals(1, savedEvents.get(0).version);
        assertEquals("first name", ((TickPostedEvent)savedEvents.get(0)).getInstrument());
        assertEquals(2, savedEvents.get(1).version);
        assertEquals("second name", ((TickPostedEvent)savedEvents.get(1)).getInstrument());
        verify(publisher).publish(aggregateId, evt1);
        verify(publisher).publish(aggregateId, evt2);
    }

    @Test
    public void saveEventsForExistingAggregate() {
        EventStore eventStore = new InMemoryEventStore(publisher);
        UUID aggregateId = UUID.randomUUID();
        List<Event> existingEvents = new ArrayList<>();

        TickPostedEvent evt1 = TickPostedEvent.create(aggregateId, "first name", new BigDecimal("9.0"), 145686655);
        evt1.version = 1;

        TickPostedEvent evt2 = TickPostedEvent.create(aggregateId, "first name", new BigDecimal("9.0"), 145686655);
        evt2.version = 2;

        existingEvents.add(evt1);
        existingEvents.add(evt2);

        eventStore.save(aggregateId, existingEvents, 0);

        TickPostedEvent evt3 = TickPostedEvent.create(aggregateId, "third name", new BigDecimal("9.0"), 145686655);
        evt3.version = 3;

        TickPostedEvent evt4 = TickPostedEvent.create(aggregateId, "fourth name", new BigDecimal("9.0"), 145686655);
        evt4.version = 4;

        List<Event> newEvents = new ArrayList<>();
        newEvents.add(evt3);
        newEvents.add(evt4);

        eventStore.save(aggregateId, newEvents, 2);

        List<? extends Event> savedEvents = eventStore.load(aggregateId);

        assertEquals(4, savedEvents.size());
        assertEquals(3, savedEvents.get(2).version);
        assertEquals("third name", ((TickPostedEvent)savedEvents.get(2)).getInstrument());
        assertEquals(4, savedEvents.get(3).version);
        assertEquals("fourth name", ((TickPostedEvent)savedEvents.get(3)).getInstrument());
        verify(publisher).publish(aggregateId, evt3);
        verify(publisher).publish(aggregateId, evt4);
    }


}
