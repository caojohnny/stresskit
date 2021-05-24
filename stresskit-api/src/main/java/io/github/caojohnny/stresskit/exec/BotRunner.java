package io.github.caojohnny.stresskit.exec;

import io.github.caojohnny.stresskit.factory.BotFactory;

/**
 * A {@link BotRunner} is a component belonging to an
 * {@link BotFactory} which handles procedures and
 * and managing each procedures' remaining tasks.
 */
public interface BotRunner {
    /**
     * Initializes the {@link BotRunner} when the factory
     * is initialized.
     *
     * @param factory the {@link BotFactory} for which this
     *                {@link BotRunner} has been created
     */
    void init(BotFactory factory);

    /**
     * Cleans up any resources and halts any remaining
     * tasks being processed by the {@link BotRunner}
     * when the associated {@link BotFactory} shuts down.
     *
     * @param factory the {@link BotFactory} that this
     *                {@link BotRunner} was assigned to
     */
    void close(BotFactory factory);
}
