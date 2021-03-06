package org.soloactive.core.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.soloactive.core.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class AggregateRoot {

    private final List<Event> changes = new ArrayList<>();
    protected UUID id;
    protected int version;

    protected AggregateRoot(UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }

    public int getVersion() { return version; }

    public int getOriginalVersion() { return version - changes.size(); }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    public final Iterable<Event> getUncommittedChanges() {
        return changes;
    }

    public final void loadFromHistory(Iterable<? extends Event> history) {
        for (Event e : history) {
            if(version + 1 == e.version) {
                version = e.version;
            }
            applyChange(e, false);
        }
    }

    protected void raise(Event event) {
        applyChange(event, true);
    }

    private void applyChange(Event event, boolean isNew) {
        invokeApplyIfEntitySupports(event);
        if (isNew) {
            version++;
            event.version = version;
            changes.add(event);
        }
    }

    private void invokeApplyIfEntitySupports(Event event) {
        Class<?> eventType = nonAnonymous(event.getClass());
        try {
            Method method = this.getClass().getDeclaredMethod("apply", eventType);
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (SecurityException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            // Ugly exception swallowing. This should be logged somewhere
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> nonAnonymous(Class<T> cl) {
        return cl.isAnonymousClass() ? (Class<T>) cl.getSuperclass() : cl;
    }
}
