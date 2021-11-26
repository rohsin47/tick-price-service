package org.soloactive.tick.event;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.event.Event;
import org.soloactive.core.event.EventHandler;
import org.soloactive.tick.repository.TickRepository;

@Slf4j
public class TickPostedEventHandler implements EventHandler {

    private final TickRepository repository;

    public TickPostedEventHandler(TickRepository repository){
        this.repository = repository;
    }

    @Override
    public void handle(Event event) {
        TickPostedEvent evt = (TickPostedEvent) event;
        log.info("Saving to read repo {}", event);
        repository.save(evt);
    }
}
