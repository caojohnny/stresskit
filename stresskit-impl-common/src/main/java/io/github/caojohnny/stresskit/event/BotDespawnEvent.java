package io.github.caojohnny.stresskit.event;

import io.github.caojohnny.stresskit.bot.Bot;

/**
 * This event is fired whenever a {@link Bot} despawns.
 */
public class BotDespawnEvent extends BotEventImpl {
    public BotDespawnEvent(Bot bot) {
        super(bot);
    }
}
