package org.soloactive.core.event;

import java.util.UUID;

public interface EventPublisher {

    void publish(UUID aggregateId, Event e);
}
