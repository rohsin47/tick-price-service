package org.soloactive.tick.command;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.command.Command;
import org.soloactive.core.command.CommandDispatcher;
import org.soloactive.core.command.CommandHandler;
import org.soloactive.core.command.CommandResolver;

@Slf4j
public class DefaultCommandDispatcher implements CommandDispatcher {

    private final CommandResolver resolver;

    public DefaultCommandDispatcher(CommandResolver resolver) {
        this.resolver = resolver;
    }

    public void dispatch(Command command) {
        CommandHandler handler = resolver.findHandlerFor(command.getClass());
        if (handler != null) {
            handler.handle(command);
        }
    }
}
