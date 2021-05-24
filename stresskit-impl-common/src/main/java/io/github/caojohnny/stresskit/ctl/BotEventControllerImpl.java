package io.github.caojohnny.stresskit.ctl;

import io.github.caojohnny.stresskit.ctl.invoke.ListenerInvoker;
import io.github.caojohnny.stresskit.ctl.invoke.MHandleListenerInvoker;
import io.github.caojohnny.stresskit.event.BotEvent;
import io.github.caojohnny.stresskit.event.BotSpawnEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * This implementation of {@link BotEventController}
 * associates event handler methods with their respective
 * listening event and dispatches events using a
 * {@link java.lang.invoke.MethodHandle}.
 *
 * <p>One interesting property is that this implementation
 * will always fire the events for super-classes. For
 * example, firing a {@link BotSpawnEvent} will also in
 * effect fire {@link BotEvent} as well, meaning that event
 * handlers listening to either of those events will be
 * invoked.</p>
 */
public class BotEventControllerImpl implements BotEventController {
    private final Map<Class<? extends BotEvent>, Collection<ListenerInvoker>> listeners =
            new HashMap<>();

    @Override
    public void addListener(@NonNull Object listener) {
        Class<?> cls = listener.getClass();
        for (Method me : cls.getDeclaredMethods()) {
            Parameter[] params = me.getParameters();
            if (params.length != 1) {
                continue;
            }

            Parameter param = params[0];
            Class<?> type = param.getType();
            if (!BotEvent.class.isAssignableFrom(type)) {
                continue;
            }

            me.setAccessible(true);
            try {
                ListenerInvoker li = new MHandleListenerInvoker(listener, me);
                listeners.computeIfAbsent((Class<? extends BotEvent>) type, k -> new ArrayList<>()).add(li);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeListener(@NonNull Object listener) {
        listeners.values().removeIf(c -> {
            c.removeIf(e -> e.getListener() == listener);
            return c.isEmpty();
        });
    }

    @Override
    public @NonNull Collection<Object> getListeners() {
        Collection<Object> listeners = new HashSet<>();
        for (Collection<ListenerInvoker> c : this.listeners.values()) {
            for (ListenerInvoker li : c) {
                listeners.add(li.getListener());
            }
        }

        return listeners;
    }

    @Override
    public void fire(@NonNull BotEvent event) {
        for (Class<?> cls = event.getClass();
             cls != null;
             cls = cls.getSuperclass()) {
            Collection<ListenerInvoker> c = this.listeners.get(cls);
            if (c != null) {
                for (ListenerInvoker li : c) {
                    try {
                        li.invoke(event);
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                }
            }
        }
    }
}
