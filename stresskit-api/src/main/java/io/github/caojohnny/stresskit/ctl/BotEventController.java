package io.github.caojohnny.stresskit.ctl;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.event.BotEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

/**
 * A {@link BotEventController} handles events and
 * listeners for the {@link Bot} for which it was
 * initialized.
 */
public interface BotEventController {
    /**
     * Adds a listener object to this
     * {@link BotEventController}.
     *
     * <p>A listener object contains event handlers, which
     * are methods whose only parameter is an event object
     * which will be injected whenever that event is fired.
     * There is no registry for events that are valid, the
     * only requirement is that they extend
     * {@link BotEvent}.</p>
     *
     * @param listener the listener to register
     */
    void addListener(@NonNull Object listener);

    /**
     * Removes a listener from this
     * {@link BotEventController}, preventing all event
     * handlers contained in that listener from receiving
     * any more events.
     *
     * @param listener the listener to remove
     */
    void removeListener(@NonNull Object listener);

    /**
     * Obtains a collection containing all the listeners
     * that have been registered to this particular
     * {@link BotEventController}.
     *
     * @return a collection of registered listeners
     */
    @NonNull
    Collection<Object> getListeners();

    /**
     * Fires an event, which calls the event handler
     * methods for all listeners for which the event is
     * handled.
     *
     * @param event the event to fire
     */
    void fire(@NonNull BotEvent event);
}
