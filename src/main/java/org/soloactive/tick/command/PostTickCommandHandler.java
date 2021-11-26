package org.soloactive.tick.command;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.command.Command;
import org.soloactive.core.command.CommandHandler;
import org.soloactive.core.event.store.EventStore;
import org.soloactive.tick.domain.Tick;

@Slf4j
public class PostTickCommandHandler implements CommandHandler {

    private EventStore eventStore;

    public PostTickCommandHandler(EventStore repository) {
        this.eventStore = repository;
    }

    @Override
    public void handle(Command command) {
        PostTickCommand postTickCommand = PostTickCommand.create((PostTickCommand) command);
        Tick item = Tick.create(postTickCommand.aggregateId, postTickCommand.instrument, postTickCommand.price, postTickCommand.timestamp);
        eventStore.save(item.getId(), item.getUncommittedChanges(), item.getOriginalVersion());
    }
}