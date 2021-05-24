package io.github.caojohnny.stresskit.v1_14_4.factory;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.factory.BotFactoryImpl;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The stock factory for creating {@link BotPlayer}
 * objects.
 */
public abstract class BotPlayerFactory extends BotFactoryImpl {
    private static int botCounter = 0;

    public BotPlayerFactory(BotRunner runner) {
        super(runner);
    }

    /**
     * Obtains the server address that created
     * {@link BotPlayer}s will connect to when they are
     * {@link Bot#spawn()}ed.
     *
     * @return the server address to which created
     * {@link BotPlayer} connect
     */
    public abstract String getServerAddress();

    /**
     * Obtains the server port that created
     * {@link BotPlayer}s will connect to when they are
     * {@link Bot#spawn()}ed.
     *
     * @return the server port to which created
     * {@link BotPlayer} connect
     */
    public abstract int getServerPort();

    @Override
    public @NonNull BotPlayer createBot() {
        BotPlayer player = new BotPlayer(this, "BPF-" + botCounter++);
        this.getCreated().put(player.getName(), player);

        return player;
    }
}
