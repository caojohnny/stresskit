package io.github.caojohnny.stresskit.factory;

import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import io.github.caojohnny.stresskit.v1_14_4.factory.BotPlayerFactory;
import org.bukkit.Bukkit;

/**
 * A {@link BukkitBotPlayerFactory} connects
 * {@link BotPlayer}s to the server on which the plugin
 * utilizing {@code stresskit} is running.
 */
public class BukkitBotPlayerFactory extends BotPlayerFactory {
    private static final String LOCALHOST_ADDR = "127.0.0.1";

    public BukkitBotPlayerFactory(BotRunner runner) {
        super(runner);
    }

    @Override
    public String getServerAddress() {
        return LOCALHOST_ADDR;
    }

    @Override
    public int getServerPort() {
        return Bukkit.getPort();
    }
}
