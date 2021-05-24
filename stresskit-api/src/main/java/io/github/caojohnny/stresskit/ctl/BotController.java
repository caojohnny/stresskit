package io.github.caojohnny.stresskit.ctl;

import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.task.Task;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link BotController} controls the actions of a
 * {@link Bot} and is linked specifically to the
 * {@link Bot} object for which it was created.
 */
public interface BotController {
    /**
     * Obtains the owner {@link Bot}
     *
     * @return the {@link Bot} for which this controller
     * was created for
     */
    @NonNull
    Bot getBot();

    /**
     * Sets the entity ID number as received from the
     * server that the bot spawned in.
     *
     * @param eid the entity ID number
     */
    void setEid(int eid);

    /**
     * Obtains the entity ID number, or returns -1 if it
     * has not yet been set.
     *
     * @return the entity ID or -1
     */
    int getEid();

    /**
     * This method should be called when the entity has
     * completely finished spawning and is ready to be
     * controlled.
     */
    void confirmSpawn();

    /**
     * Checks to determine if spawning has completed.
     *
     * <p>This is useful in {@link Task}s where it is not
     * known when exactly a player entity, for example, has
     * completely finished spawning on the server.</p>
     *
     * @return {@code true} if the entity has completed
     * spawning
     */
    boolean isSpawnConfirmed();

    /**
     * Obtains the current X coordinate of the {@link Bot}
     *
     * @return the X coordinate
     */
    double getX();

    /**
     * Obtains the current Y coordinate of the {@link Bot}
     *
     * @return the Y coordinate
     */
    double getY();

    /**
     * Obtains the current Z coordinate of the {@link Bot}
     *
     * @return the Z coordinate
     */
    double getZ();

    /**
     * Obtains the current pitch of the {@link Bot}
     *
     * @return the pitch coordinate
     */
    float getPitch();

    /**
     * Obtains the current yaw of the {@link Bot}
     *
     * @return the yaw coordinate
     */
    float getYaw();

    /**
     * Obtains the X component of the {@link Bot}'s
     * velocity vector.
     *
     * @return the X component of the velocity
     */
    double getVelocityX();

    /**
     * Obtains the Y component of the {@link Bot}'s
     * velocity vector.
     *
     * @return the Y component of the velocity
     */
    double getVelocityY();

    /**
     * Obtains the Z component of the {@link Bot}'s
     * velocity vector.
     *
     * @return the Z component of the velocity
     */
    double getVelocityZ();

    /**
     * Updates the position of the {@link Bot} based on
     * response from the server.
     *
     * @param x          the new x coordinate
     * @param y          the new y coordinate
     * @param z          the new z coordinate
     * @param pitch      the new pitch
     * @param yaw        the new yaw
     * @param isXRel     {@code true} if the provided X
     *                   coordinate is relative
     * @param isYRel     {@code true} if the provided Y
     *                   coordinate is relative
     * @param isZRel     {@code true} if the provided Z
     *                   coordinate is relative
     * @param isPitchRel {@code true} if the provided
     *                   pitch is relative
     * @param isYawRel   {@code true} if the provided
     *                   yaw is relative
     */
    void updatePosition(double x, double y, double z, float pitch, float yaw,
                        boolean isXRel, boolean isYRel, boolean isZRel, boolean isPitchRel, boolean isYawRel);

    /**
     * Updates the velocity of the {@link Bot} based on
     * response from the server
     *
     * @param vx the new velocity X component
     * @param vy the new velocity Y component
     * @param vz the new velocity Z component
     */
    void updateVelocity(double vx, double vy, double vz);

    /**
     * Teleports the entity, requesting the server move it
     * to the new specified location.
     *
     * @param x     the new requested X coordinate
     * @param y     the new requested Y coordinate
     * @param z     the new requested Z coordinate
     * @param pitch the new requested pitch
     * @param yaw   the new requested yaw
     */
    void teleport(double x, double y, double z, float pitch, float yaw);
}
