package io.github.caojohnny.stresskit.event.player;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.event.BotDespawnEvent;

/**
 * This event is an extension of {@link BotDespawnEvent}
 * which allows users to retrieve the disconnect message.
 */
public class BotDisconnectEvent extends BotDespawnEvent {
    private final String message;

    public BotDisconnectEvent(Bot bot, String message) {
        super(bot);
        this.message = message;
    }

    /**
     * Obtains the disconnect message.
     *
     * @return the message contained in the disconnect
     * packet.
     */
    public String getMessage() {
        return this.message;
    }
}
