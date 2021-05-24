package io.github.caojohnny.stresskit.v1_14_4.behavior;

import com.github.steveice10.mc.protocol.data.game.entity.player.PositionElement;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityVelocityPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.*;
import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import io.github.caojohnny.stresskit.event.BotSpawnEvent;
import io.github.caojohnny.stresskit.event.player.BotDisconnectEvent;
import io.github.caojohnny.stresskit.event.player.BotPacketEvent;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;

import java.util.EnumSet;
import java.util.Set;

/**
 * This class contains some helper listeners to allow the
 * stock events contained in
 * {@link io.github.caojohnny.stresskit.event}
 * package to function and basic handling for the
 * {@link BotController} packets.
 *
 * @see #enable(BotPlayer)
 */
public final class DefaultPlayerBehaviors {
    private DefaultPlayerBehaviors() {
    }

    /**
     * Registers all the needed listeners to enable the
     * default behavior for the given {@link BotPlayer}.
     *
     * @param bot the {@link BotPlayer} for which the
     *            behavior modifications will be added
     */
    public static void enable(BotPlayer bot) {
        BotEventController evCtl = bot.getEventController();
        evCtl.addListener(new Object() {
            public void onPacket(BotPacketEvent event) {
                Object packet = event.getPacket();
                BotController controller = event.getBot().getController();
                if (packet instanceof ServerJoinGamePacket) {
                    ServerJoinGamePacket joinGame = (ServerJoinGamePacket) packet;
                    controller.setEid(joinGame.getEntityId());
                }

                if (packet instanceof ServerPlayerPositionRotationPacket) {
                    ServerPlayerPositionRotationPacket posRot = (ServerPlayerPositionRotationPacket) packet;
                    Set<PositionElement> rel = posRot.getRelative().isEmpty() ? EnumSet.noneOf(PositionElement.class) : EnumSet.copyOf(posRot.getRelative());

                    controller.updatePosition(posRot.getX(), posRot.getY(), posRot.getZ(), posRot.getPitch(), posRot.getYaw(),
                            rel.contains(PositionElement.X), rel.contains(PositionElement.Y), rel.contains(PositionElement.Z),
                            rel.contains(PositionElement.PITCH), rel.contains(PositionElement.YAW));
                    controller.confirmSpawn();

                    ClientTeleportConfirmPacket tpConfirm = new ClientTeleportConfirmPacket(posRot.getTeleportId());
                    Session session = ((BotPlayer) event.getBot()).getClient().getSession();
                    session.send(tpConfirm);
                }

                if (packet instanceof ServerEntityVelocityPacket) {
                    ServerEntityVelocityPacket vel = (ServerEntityVelocityPacket) packet;
                    if (vel.getEntityId() == controller.getEid()) {
                        controller.updateVelocity(vel.getMotionX(), vel.getMotionY(), vel.getMotionZ());
                    }
                }
            }

            public void onDisconnect(BotDisconnectEvent event) {
                event.getBot().despawn();
            }
        });

        Client client = bot.getClient();
        Session session = client.getSession();

        session.addListener(new SessionAdapter() {
            @Override
            public void connected(ConnectedEvent event) {
                BotEventController evCtl = bot.getEventController();
                evCtl.fire(new BotSpawnEvent(bot));
            }

            @Override
            public void packetSending(PacketSendingEvent event) {
                BotEventController evCtl = bot.getEventController();
                evCtl.fire(new BotPacketEvent(bot, event.getPacket(), BotPacketEvent.Bound.SERVER));
            }

            @Override
            public void packetReceived(PacketReceivedEvent event) {
                BotEventController evCtl = bot.getEventController();
                evCtl.fire(new BotPacketEvent(bot, event.getPacket(), BotPacketEvent.Bound.CLIENT));
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                BotEventController evCtl = bot.getEventController();
                evCtl.fire(new BotDisconnectEvent(bot, event.getReason()));
            }
        });
    }
}
