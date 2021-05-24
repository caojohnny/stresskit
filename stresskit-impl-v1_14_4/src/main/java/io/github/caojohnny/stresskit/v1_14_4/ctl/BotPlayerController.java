package io.github.caojohnny.stresskit.v1_14_4.ctl;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BotPlayerController implements BotController {
    private final BotPlayer bot;

    private int eid = -1;

    private boolean isSpawnConfirmed;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    private double vx;
    private double vy;
    private double vz;

    public BotPlayerController(@NonNull BotPlayer bot) {
        this.bot = bot;
    }

    @Override
    public @NonNull BotPlayer getBot() {
        return this.bot;
    }

    @Override
    public void setEid(int eid) {
        this.eid = eid;
    }

    @Override
    public int getEid() {
        return this.eid;
    }

    @Override
    public void confirmSpawn() {
        this.isSpawnConfirmed = true;
    }

    @Override
    public boolean isSpawnConfirmed() {
        return this.getBot().getClient() != null && this.isSpawnConfirmed;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public double getVelocityX() {
        return this.vx;
    }

    @Override
    public double getVelocityY() {
        return this.vy;
    }

    @Override
    public double getVelocityZ() {
        return this.vz;
    }

    @Override
    public void updatePosition(double x, double y, double z, float pitch, float yaw,
                               boolean isXRel, boolean isYRel, boolean isZRel, boolean isPitchRel, boolean isYawRel) {
        this.x = isXRel ? this.x + x : x;
        this.y = isYRel ? this.y + y : y;
        this.z = isZRel ? this.z + z : z;
        this.pitch = isPitchRel ? this.pitch + pitch : pitch;
        this.yaw = isYawRel ? this.yaw + yaw : yaw;
    }

    @Override
    public void updateVelocity(double vx, double vy, double vz) {

    }

    @Override
    public void teleport(double x, double y, double z, float pitch, float yaw) {
        Client client = this.bot.getClient();
        Session session = client.getSession();

        ClientPlayerPositionRotationPacket packet = new ClientPlayerPositionRotationPacket(true, x, y, z, yaw, pitch);
        session.send(packet);

        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
}
