package io.github.caojohnny.stresskit.task;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.proc.Procedure;
import org.checkerframework.checker.nullness.qual.NonNull;

@FunctionalInterface
public interface TaskAdapter extends Task {
    @Override
    default void init(@NonNull Bot bot, @NonNull Procedure procedure) {
    }

    @Override
    void tick(@NonNull Bot bot, @NonNull Procedure procedure);

    @Override
    default void close(@NonNull Bot bot, @NonNull Procedure procedure) {
    }
}
