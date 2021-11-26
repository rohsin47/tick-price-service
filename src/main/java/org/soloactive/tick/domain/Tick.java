package org.soloactive.tick.domain;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.aggregate.AggregateRoot;
import org.soloactive.tick.event.TickPostedEvent;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
public class Tick extends AggregateRoot {

    private String instrument;
    private BigDecimal price;
    private long timestamp;

    public Tick(UUID aggregateId) {
        super(aggregateId);
    }

    private Tick(UUID aggregateId, String instrument, BigDecimal price, long timestamp) {
        super(aggregateId);
        raise(TickPostedEvent.create(aggregateId, instrument, price, timestamp));
    }

    public static Tick create(UUID aggregateId, String instrument, BigDecimal price, long timestamp) {
        return new Tick(aggregateId, instrument, price, timestamp);
    }

    private void apply(TickPostedEvent evt) {
        log.info("Applied evt {}", evt);
        this.instrument = evt.instrument;
        this.price = evt.price;
        this.timestamp = evt.postedTime;
    }

    @Override
    public String toString() {
        return "Tick{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", instrument='" + instrument + '\'' +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
