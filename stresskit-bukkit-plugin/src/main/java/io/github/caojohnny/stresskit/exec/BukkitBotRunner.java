package io.github.caojohnny.stresskit.exec;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.factory.BotFactory;
import io.github.caojohnny.stresskit.proc.Procedure;
import io.github.caojohnny.stresskit.task.Task;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * A {@link BukkitBotRunner} utilizes Bukkit's
 * {@link BukkitScheduler} in order to run {@link Bot}
 * tasks.
 */
public class BukkitBotRunner implements BotRunner {
    private final Plugin plugin;

    private BukkitRunnable br;

    public BukkitBotRunner(Plugin plugin) {
        this.plugin = plugin;
    }

    private static void tickBot(Bot bot) {
        Procedure procedure = bot.getProcedure();
        if (procedure == null) {
            return;
        }

        for (Task task : procedure.getRemainingTasks()) {
            task.tick(bot, procedure);
        }
    }

    @Override
    public void init(BotFactory factory) {
        this.br = new BukkitRunnable() {
            @Override
            public void run() {
                for (Bot bot : factory.getCreated().values()) {
                    tickBot(bot);
                }
            }
        };

        this.br.runTaskTimer(this.plugin, 0L, 1L);
    }

    @Override
    public void close(BotFactory factory) {
        if (this.br == null) {
            throw new IllegalStateException("Not initialized yet.");
        }

        this.br.cancel();
    }
}
