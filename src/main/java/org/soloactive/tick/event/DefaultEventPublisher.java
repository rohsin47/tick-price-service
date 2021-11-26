package org.soloactive.tick.event;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.event.Event;
import org.soloactive.core.event.EventHandler;
import org.soloactive.core.event.EventPublisher;
import org.soloactive.core.event.EventResolver;

import java.util.UUID;

@Slf4j
public class DefaultEventPublisher implements EventPublisher {

    private final EventResolver resolver;

    public DefaultEventPublisher(EventResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void publish(UUID aggregateId, Event event) {
        Iterable<EventHandler> eventHandlers = resolver.findHandlersFor(event.getClass());
        for (EventHandler eventHandler : eventHandlers) {
            eventHandler.handle(event);
        }
    }
}
