package org.soloactive.core.event;

import java.util.UUID;

public abstract class Event {
    public UUID aggregateId;
    public int version;
}
