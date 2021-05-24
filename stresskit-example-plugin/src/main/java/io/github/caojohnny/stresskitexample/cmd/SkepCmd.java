package io.github.caojohnny.stresskitexample.cmd;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import io.github.caojohnny.stresskit.proc.ProcedureImpl;
import io.github.caojohnny.stresskit.task.Task;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import io.github.caojohnny.stresskit.v1_14_4.factory.BotPlayerFactory;
import io.github.caojohnny.stresskitexample.listener.BotPlayerListener;
import io.github.caojohnny.stresskitexample.task.WalkSouthTask;
import io.github.caojohnny.stresskitexample.util.SkepMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Map;

public class SkepCmd implements CommandExecutor {
    private static final String SKEP_CMD_PERMISSION = "skep.admin";
    private static final int N_BOTS = 20;

    private final BotPlayerFactory factory;

    private boolean isInProgress;

    public SkepCmd(BotPlayerFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command cmd, @NonNull String label, String @NonNull [] args) {
        if (!sender.hasPermission(SKEP_CMD_PERMISSION)) {
            SkepMessages.NO_PERMISSION.sendTo(sender);
            return true;
        }

        if (this.isInProgress) {
            this.isInProgress = false;
            SkepMessages.SKEP_END.sendTo(sender);
            this.endTest();

            return true;
        }

        this.isInProgress = true;
        SkepMessages.START_TEST.sendTo(sender);
        SkepMessages.HELP_END.sendTo(sender);
        this.beginTest();

        return true;
    }

    public void beginTest() {
        for (int i = 0; i < N_BOTS; i++) {
            BotPlayer bot = this.factory.createBot();

            BotEventController evCtl = bot.getEventController();
            evCtl.addListener(BotPlayerListener.INSTANCE);

            ProcedureImpl proc = new ProcedureImpl();
            Collection<Task> tasks = proc.getRemainingTasks();
            tasks.add(WalkSouthTask.INSTANCE);

            bot.setProcedure(proc);
            bot.spawn();
        }
    }

    private void endTest() {
        Map<String, Bot> created = this.factory.getCreated();
        for (Bot bot : created.values()) {
            bot.despawn();
        }
    }
}
