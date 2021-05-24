package io.github.caojohnny.stresskit.factory;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.exec.BotRunner;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * A {@link BotFactory} is the entry point for the
 * {@code stresskit} API and allows users to create
 * {@link Bot} objects for stress testing servers with
 * bots capable of mimicking actual players.
 */
public interface BotFactory {
    /**
     * Called by users to initialize any resources such as
     * {@link BotRunner}s used by this factory.
     */
    void init();

    /**
     * Obtains the {@link BotRunner} that this factory uses
     * to manage {@link Bot}s it creates.
     *
     * @return the {@link BotRunner} being used by this
     * {@link BotFactory}
     */
    @NonNull
    BotRunner getRunner();

    /**
     * Creates a new {@link Bot} object that can be
     * customized to the needs of the user using the
     * options set through this {@link BotFactory}.
     *
     * @return the new {@link Bot} object
     */
    @NonNull
    Bot createBot();

    /**
     * Obtains a mapping of each {@link Bot}'s name and
     * the {@link Bot} that was created by this
     * {@link BotFactory}.
     *
     * @return the bot name to object mapping
     */
    @NonNull
    Map<String, Bot> getCreated();

    /**
     * Cleans up and removes any bots created by this
     * {@link BotFactory}, despawning them and the
     * resources each {@link Bot} is associated with.
     */
    void close();
}
