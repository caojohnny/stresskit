package io.github.caojohnny.stresskit.bot;

import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import io.github.caojohnny.stresskit.factory.BotFactory;
import io.github.caojohnny.stresskit.proc.Procedure;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a controllable bot that is used for a stress
 * test.
 */
public interface Bot {
    /**
     * Obtains the {@link BotFactory} that created this
     * {@link Bot} object.
     *
     * @return the creating factory
     */
    @NonNull
    BotFactory getFactory();

    /**
     * Sets the name of the {@link Bot}.
     *
     * <p>You should avoid setting the name of a
     * {@link Bot} once it has been spawned. Despawn it
     * first before renaming.</p>
     *
     * @param name the new name of the {@link Bot}
     */
    void setName(@NonNull String name);

    /**
     * Obtains the name of the bot, either set by default
     * from the creating {@link BotFactory} or through the
     * {@link #setName(String)} method.
     *
     * @return the name currently being used
     */
    @NonNull
    String getName();

    /**
     * Sets the new {@link Procedure} to be used by this
     * {@link Bot}, replacing any previous
     * {@link Procedure} and doing any appropriate
     * initialization and teardown operations.
     *
     * @param procedure the new {@link Procedure} to be
     *                  used by this {@link Bot}
     */
    void setProcedure(@Nullable Procedure procedure);

    /**
     * Obtains the {@link Procedure} currently being used
     * by this {@link Bot}.
     *
     * @return the current procedure
     */
    @Nullable
    Procedure getProcedure();

    /**
     * Obtains the {@link BotController} object associated
     * with this particular {@link Bot} used to control its
     * actions.
     *
     * @return the {@link BotController} created for use
     * with this {@link Bot}
     */
    @NonNull
    BotController getController();

    /**
     * Obtains the {@link BotEventController} object
     * associated with this {@link Bot} used to control
     * event listeners and dispatch events.
     *
     * @return the {@link BotEventController} created for
     * use with this particular {@link Bot}
     */
    @NonNull
    BotEventController getEventController();

    /**
     * Spawns the {@link Bot}, adding its entity to the
     * server by connecting or spawning.
     */
    void spawn();

    /**
     * Despawns this {@link Bot}, removing the entity and
     * doing any appropriate cleanup operations.
     */
    void despawn();
}
