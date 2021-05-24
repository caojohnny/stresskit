package io.github.caojohnny.stresskitexample.listener;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.packetlib.Session;
import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.event.player.BotDisconnectEvent;
import io.github.caojohnny.stresskit.event.player.BotPacketEvent;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;

public final class BotPlayerListener {
    public static final BotPlayerListener INSTANCE = new BotPlayerListener();

    private BotPlayerListener() {
    }

    public void onPacket(BotPacketEvent ev) {
        BotPacketEvent.Bound bound = ev.getBound();
        if (bound == BotPacketEvent.Bound.SERVER) {
            return;
        }

        Object packet = ev.getPacket();
        if (packet instanceof ServerPlayerHealthPacket) {
            float health = ((ServerPlayerHealthPacket) packet).getHealth();
            if (health > 0) {
                return;
            }

            BotPlayer bot = (BotPlayer) ev.getBot();
            Session session = bot.getClient().getSession();
            session.send(new ClientRequestPacket(ClientRequest.RESPAWN));
        }
    }

    public void onDespawn(BotDisconnectEvent ev) {
        Bot bot = ev.getBot();
        String message = ev.getMessage();
        System.out.println(bot.getName() + " has disconnected: " + message);
    }
}
