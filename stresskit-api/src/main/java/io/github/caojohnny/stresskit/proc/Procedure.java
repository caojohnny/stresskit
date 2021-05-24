package io.github.caojohnny.stresskit.proc;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.task.Task;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

/**
 * Represents a collection of tasks that each {@link Bot}
 * is capable of running.
 */
public interface Procedure {
    /**
     * Initializes this {@link Procedure}, running once
     * when it is added to a {@link Bot} object.
     *
     * @param bot the bot for which this procedure was
     *            created
     */
    void init(@NonNull Bot bot);

    /**
     * Obtains the tasks left in this procedure.
     *
     * @return the tasks that still need to be executed by
     * a {@link BotRunner}
     */
    @NonNull
    Collection<Task> getRemainingTasks();

    /**
     * Cleans up any resources used by this
     * {@link Procedure} when the given {@link Bot} has a
     * new {@link Procedure}.
     *
     * @param bot the {@link Bot} for which this
     *            {@link Procedure} is being cleaned up
     */
    void close(@NonNull Bot bot);
}
