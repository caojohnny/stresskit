package io.github.caojohnny.stresskit.event;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link BotEvent} is the superclass for all events that
 * can be handled by a {@link BotEventController}
 */
public interface BotEvent {
    /**
     * Obtains the {@link Bot} that was the object of
     * concern for the event called.
     *
     * @return the {@link Bot} object
     */
    @NonNull
    Bot getBot();
}
