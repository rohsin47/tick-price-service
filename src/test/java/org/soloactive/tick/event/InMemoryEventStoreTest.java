package org.soloactive.tick.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.soloactive.core.event.Event;
import org.soloactive.core.event.EventPublisher;
import org.soloactive.core.event.store.InMemoryEventStore;
import org.soloactive.tick.domain.Tick;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryEventStoreTest {

    @Mock
    EventPublisher eventPublisher;

    @InjectMocks
    InMemoryEventStore eventStore;

    @Test
    public void saveNewAggregate() {
        UUID aggregateId = UUID.randomUUID();
        Tick aggregate = Tick.create(aggregateId, "ins", new BigDecimal("9.0"), 1432535436);

        Iterable<Event> events = aggregate.getUncommittedChanges();

        assertEquals(1, aggregate.getVersion());
        assertEquals(0, aggregate.getOriginalVersion());

        doNothing().when(eventPublisher).publish(any(), any());

        eventStore.save(aggregateId, events, aggregate.getOriginalVersion());

        verify(eventPublisher).publish(any(), any());
    }

}
