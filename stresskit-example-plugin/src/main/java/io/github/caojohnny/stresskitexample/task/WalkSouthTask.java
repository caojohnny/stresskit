package io.github.caojohnny.stresskitexample.task;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.proc.Procedure;
import io.github.caojohnny.stresskit.task.TaskAdapter;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class WalkSouthTask implements TaskAdapter {
    public static final WalkSouthTask INSTANCE = new WalkSouthTask();

    private WalkSouthTask() {
    }

    @Override
    public void tick(@NonNull Bot bot, @NonNull Procedure procedure) {
        BotPlayer bp = (BotPlayer) bot;
        BotController controller = bp.getController();
        if (!controller.isSpawnConfirmed()) {
            return;
        }

        controller.teleport(controller.getX(), controller.getY(), controller.getZ() + 0.3,
                controller.getPitch(), controller.getYaw());
    }
}
