package io.github.caojohnny.stresskit.v1_14_4.bot;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import io.github.caojohnny.stresskit.ctl.BotEventControllerImpl;
import io.github.caojohnny.stresskit.factory.BotFactory;
import io.github.caojohnny.stresskit.proc.Procedure;
import io.github.caojohnny.stresskit.v1_14_4.behavior.DefaultPlayerBehaviors;
import io.github.caojohnny.stresskit.v1_14_4.ctl.BotPlayerController;
import io.github.caojohnny.stresskit.v1_14_4.factory.BotPlayerFactory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;

/**
 * This is the stock implementation of a {@link Bot} that
 * represents a player.
 */
public class BotPlayer implements Bot {
    public static final String DESPAWN_MSG = "Bot#despawn()";
    private final BotController controller;
    private final BotEventController eventController =
            new BotEventControllerImpl();

    private final BotPlayerFactory factory;
    private String name;

    private Procedure procedure;
    private Client client;

    public BotPlayer(@NonNull BotPlayerFactory factory, @NonNull String name) {
        this.controller = new BotPlayerController(this);

        this.factory = factory;
        this.name = name;
    }

    @Override
    public @NonNull BotFactory getFactory() {
        return this.factory;
    }

    @Override
    public void setName(@NonNull String name) {
        if (this.client != null) {
            throw new IllegalStateException("Cannot set name while connected");
        }

        Map<String, Bot> created = this.factory.getCreated();
        created.remove(this.name);
        this.name = name;
        created.put(this.name, this);
    }

    @Override
    public @NonNull String getName() {
        return this.name;
    }

    @Override
    public void setProcedure(@Nullable Procedure procedure) {
        Procedure prevProcedure = this.procedure;
        if (prevProcedure != null) {
            prevProcedure.close(this);
        }

        this.procedure = procedure;
        if (procedure != null) {
            procedure.init(this);
        }
    }

    @Override
    public @Nullable Procedure getProcedure() {
        return this.procedure;
    }

    @Override
    public @NonNull BotController getController() {
        return this.controller;
    }

    @Override
    public @NonNull BotEventController getEventController() {
        return this.eventController;
    }

    @Override
    public void spawn() {
        if (this.client != null) {
            throw new IllegalStateException("Already connected");
        }

        String host = this.factory.getServerAddress();
        int port = this.factory.getServerPort();
        MinecraftProtocol clientProto = new MinecraftProtocol(this.name);
        TcpSessionFactory tsf = new TcpSessionFactory();
        Client client = new Client(host, port, clientProto, tsf);

        this.client = client;
        DefaultPlayerBehaviors.enable(this);

        Session session = client.getSession();
        session.connect(false);
    }

    @Override
    public void despawn() {
        Client client = this.client;
        if (client == null) {
            return;
        }

        Session session = client.getSession();
        session.disconnect(DESPAWN_MSG);

        this.procedure.close(this);
        this.procedure = null;

        this.client = null;
    }

    /**
     * Obtains the MCProtocolLib {@link Client} backing the
     * {@link BotPlayer}'s connection to the server.
     *
     * @return the {@link Client} connection, or
     * {@code null} if not currently connected
     */
    public @Nullable Client getClient() {
        return this.client;
    }
}
