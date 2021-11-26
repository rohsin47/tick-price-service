package org.soloactive.tick.event;

import org.soloactive.core.event.Event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;

public class TickPostedEvent extends Event {

    public String instrument;
    public BigDecimal price;
    public long postedTime;
    public ZonedDateTime creationTime;

    public static TickPostedEvent create(UUID aggregateId, String instrument, BigDecimal price, long timestamp) {
        TickPostedEvent evt = new TickPostedEvent();
        evt.aggregateId = aggregateId;
        evt.instrument = instrument;
        evt.price = price;
        evt.postedTime = timestamp;
        evt.creationTime = ZonedDateTime.now();
        return evt;
    }

    public String getInstrument() {
        return instrument;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getPostedTime() {
        return postedTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    private static ZonedDateTime toTime(long timestamp){
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
    }

    @Override
    public String toString() {
        return "TickPostedEvent{" +
                "aggregateId='" + aggregateId + '\'' +
                ", version=" + version +
                ", instrument='" + instrument + '\'' +
                ", price=" + price +
                ", postedTime=" + postedTime +
                ", creationTime=" + creationTime +
                '}';
    }
}
