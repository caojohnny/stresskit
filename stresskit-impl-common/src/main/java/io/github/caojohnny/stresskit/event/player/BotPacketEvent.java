package io.github.caojohnny.stresskit.event.player;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.event.BotEventImpl;

/**
 * This event is fired whenever a packet is sent to or from
 * a {@link Bot}.
 */
public class BotPacketEvent extends BotEventImpl {
    private final Object packet;
    private final Bound bound;

    public BotPacketEvent(Bot bot, Object packet, Bound bound) {
        super(bot);
        this.packet = packet;
        this.bound = bound;
    }

    /**
     * Obtains the MCProtocolLib wrapper for the packet.
     *
     * @return the packet
     */
    public Object getPacket() {
        return this.packet;
    }

    /**
     * Obtains the direction in which the packet was
     * travelling.
     *
     * @return the packet bound
     */
    public Bound getBound() {
        return this.bound;
    }

    /**
     * Represents the direction which a packet is destined
     * towards.
     */
    public enum Bound {
        /**
         * Represents a server-bound packet.
         */
        SERVER,

        /**
         * Represents a client-bound packet.
         */
        CLIENT
    }
}
