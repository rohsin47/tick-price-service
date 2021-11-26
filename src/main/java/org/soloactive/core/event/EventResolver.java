package org.soloactive.core.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventResolver {

    private final Map<String, List<EventHandler>> eventHandlers = new ConcurrentHashMap<>();

    public Iterable<EventHandler> findHandlersFor(Class<?> evtClass) {
        List<EventHandler> handlers = eventHandlers.get(evtClass.getSimpleName());
        if (handlers == null)
            throw new UnsupportedOperationException(String.format("No handlers defined for event %s", evtClass.getSimpleName()));

        List<EventHandler> concreteHandlers = new ArrayList<>();
        for (EventHandler handler : handlers) {
            concreteHandlers.add((EventHandler) handler);
        }
        return concreteHandlers;
    }

    public void register(EventHandler handler, Class<?> evtClass) {
        List<EventHandler> handlers;
        if (eventHandlers.containsKey(evtClass.getSimpleName())) {
            handlers = eventHandlers.get(evtClass.getSimpleName());
        } else {
            handlers = new ArrayList<>();
            eventHandlers.put(evtClass.getSimpleName(), handlers);
        }
        handlers.add(handler);
    }
}
