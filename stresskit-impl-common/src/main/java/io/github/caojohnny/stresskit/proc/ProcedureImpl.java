package io.github.caojohnny.stresskit.proc;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.task.Task;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProcedureImpl implements Procedure {
    private final List<Task> remainingTasks = new ArrayList<>();

    @Override
    public void init(@NonNull Bot bot) {
        for (Task task : this.remainingTasks) {
            task.init(bot, this);
        }
    }

    @Override
    public @NonNull Collection<Task> getRemainingTasks() {
        return this.remainingTasks;
    }

    @Override
    public void close(@NonNull Bot bot) {
        for (Task task : this.remainingTasks) {
            task.close(bot, this);
        }

        this.remainingTasks.clear();
    }
}
