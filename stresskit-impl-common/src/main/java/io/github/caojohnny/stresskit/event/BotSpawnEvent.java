package io.github.caojohnny.stresskit.event;

import io.github.caojohnny.stresskit.bot.Bot;

/**
 * This event is called when a bot is spawned.
 */
public class BotSpawnEvent extends BotEventImpl {
    public BotSpawnEvent(Bot bot) {
        super(bot);
    }
}
