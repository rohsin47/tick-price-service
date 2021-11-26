package org.soloactive.tick.domain;

import org.soloactive.tick.TestHelper;
import org.junit.Test;
import org.soloactive.core.event.Event;
import org.soloactive.tick.event.TickPostedEvent;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TickTest {

    @Test
    public void createAggregate() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "DDD rocks!";

        //Act
        Tick aggregate = Tick.create(id, "ins", new BigDecimal("9.0"), 1432535436);

        //Assert
        assertEquals(id, aggregate.getId());
        List<Event> events = TestHelper.getEvents(aggregate);
        assertEquals(events.size(), 1);
        TickPostedEvent evt = (TickPostedEvent) events.get(0);
        assertEquals(id, evt.aggregateId);
        assertEquals(1, evt.version);
        assertEquals("ins", evt.getInstrument());
    }

}
