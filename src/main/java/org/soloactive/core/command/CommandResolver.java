package org.soloactive.core.command;

import java.util.concurrent.ConcurrentHashMap;

public class CommandResolver {

    private final static CommandResolver instance = new CommandResolver();

    public static CommandResolver getInstance() {
        return instance;
    }

    private final ConcurrentHashMap<String, CommandHandler> map = new ConcurrentHashMap<String, CommandHandler>();

    public CommandHandler findHandlerFor(Class<?> cmdClass) {
        CommandHandler handler = map.get((Object) cmdClass.getSimpleName());
        if (handler == null)
            throw new UnsupportedOperationException(String.format("No handler defined for command %s", cmdClass.getSimpleName()));

        return (CommandHandler) handler;
    }

    public void register(CommandHandler handler, Class<?> cmdClass) {
        map.put(cmdClass.getSimpleName(), handler);
    }
}
