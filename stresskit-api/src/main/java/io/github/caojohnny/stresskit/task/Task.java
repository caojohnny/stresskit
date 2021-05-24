package io.github.caojohnny.stresskit.task;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.factory.BotFactory;
import io.github.caojohnny.stresskit.proc.Procedure;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link Task} represents a discrete unit of work that
 * is executed for a {@link Bot}.
 */
public interface Task {
    /**
     * Initializes any resources for this {@link Task} once
     * when the {@link Procedure} it has been added to is
     * initialized.
     *
     * @param bot       the {@link Bot} for which this task
     *                  is being initialized
     * @param procedure the {@link Procedure} for which
     *                  this task is being initialized
     */
    void init(@NonNull Bot bot, @NonNull Procedure procedure);

    /**
     * Called periodically by a {@link BotRunner} when a
     * {@link Bot} is created by a {@link BotFactory}.
     *
     * @param bot       the {@link Bot} for which this task
     *                  is being ticked
     * @param procedure the {@link Procedure} for which
     *                  this task is being ticked
     */
    void tick(@NonNull Bot bot, @NonNull Procedure procedure);

    /**
     * Cleans up any resources for this {@link Task} once
     * when the {@link Procedure} it has been added to is
     * closed.
     *
     * @param bot       the {@link Bot} for which this task
     *                  is being closed
     * @param procedure the {@link Procedure} for which
     *                  this task is being closed
     */
    void close(@NonNull Bot bot, @NonNull Procedure procedure);
}
