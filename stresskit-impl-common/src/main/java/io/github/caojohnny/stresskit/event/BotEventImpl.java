package io.github.caojohnny.stresskit.event;

import io.github.caojohnny.stresskit.bot.Bot;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This is a super-class for other {@link BotEvent}s to
 * reduce boilerplate.
 */
public class BotEventImpl implements BotEvent {
    private final Bot bot;

    public BotEventImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public @NonNull Bot getBot() {
        return this.bot;
    }
}
