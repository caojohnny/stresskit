package io.github.caojohnny.stresskit;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.packetlib.Session;
import io.github.caojohnny.stresskit.bot.Bot;
import io.github.caojohnny.stresskit.ctl.BotController;
import io.github.caojohnny.stresskit.ctl.BotEventController;
import io.github.caojohnny.stresskit.event.player.BotDisconnectEvent;
import io.github.caojohnny.stresskit.event.player.BotPacketEvent;
import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.factory.BotFactory;
import io.github.caojohnny.stresskit.proc.Procedure;
import io.github.caojohnny.stresskit.proc.ProcedureImpl;
import io.github.caojohnny.stresskit.task.Task;
import io.github.caojohnny.stresskit.task.TaskAdapter;
import io.github.caojohnny.stresskit.v1_14_4.bot.BotPlayer;
import io.github.caojohnny.stresskit.v1_14_4.factory.BotPlayerFactory;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApiTest {
    public static void main(String[] args) {
        BotRunner runner = new BotRunner() {
            private final ScheduledExecutorService service =
                    Executors.newSingleThreadScheduledExecutor();

            @Override
            public void init(BotFactory factory) {
                service.scheduleWithFixedDelay(() -> {
                    try {
                        for (Bot bot : factory.getCreated().values()) {
                            Procedure procedure = bot.getProcedure();
                            if (procedure == null) {
                                return;
                            }

                            for (Task task : procedure.getRemainingTasks()) {
                                task.tick(bot, procedure);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 50, 50, TimeUnit.MILLISECONDS);
            }

            @Override
            public void close(BotFactory factory) {
                this.service.shutdown();
                try {
                    this.service.awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        BotPlayerFactory factory = new BotPlayerFactory(runner) {
            @Override
            public String getServerAddress() {
                return "localhost";
            }

            @Override
            public int getServerPort() {
                return 25565;
            }
        };
        factory.init();

        Object listener = new Object() {
            public void onPacket(BotPacketEvent ev) {
                BotPacketEvent.Bound bound = ev.getBound();
                if (bound == BotPacketEvent.Bound.SERVER) {
                    return;
                }

                Object packet = ev.getPacket();
                if (packet instanceof ServerPlayerHealthPacket) {
                    float health = ((ServerPlayerHealthPacket) packet).getHealth();
                    if (health > 0) {
                        return;
                    }

                    BotPlayer bot = (BotPlayer) ev.getBot();
                    Session session = bot.getClient().getSession();
                    session.send(new ClientRequestPacket(ClientRequest.RESPAWN));
                }
            }

            public void onDespawn(BotDisconnectEvent ev) {
                Bot bot = ev.getBot();
                String message = ev.getMessage();
                System.out.println(bot.getName() + " has disconnected: " + message);
            }
        };

        for (int i = 0; i < 1; i++) {
            BotPlayer bot = factory.createBot();
            BotEventController evCtl = bot.getEventController();
            evCtl.addListener(listener);

            Procedure proc = new ProcedureImpl();
            Collection<Task> tasks = proc.getRemainingTasks();
            tasks.add((TaskAdapter) (taskBot, taskProc) -> {
                BotPlayer bp = (BotPlayer) taskBot;
                BotController controller = bp.getController();
                if (!controller.isSpawnConfirmed()) {
                    return;
                }

                controller.teleport(controller.getX(), controller.getY(), controller.getZ() + 0.3,
                        controller.getPitch(), controller.getYaw());
            });
            tasks.add((TaskAdapter) (taskBot, taskProc) -> {
                BotPlayer bp = (BotPlayer) taskBot;
                BotController controller = bp.getController();
                if (!controller.isSpawnConfirmed()) {
                    return;
                }

                System.out.println("CUR POS: " + controller.getX() + "," + controller.getY() + "," + controller.getZ());
            });
            bot.setProcedure(proc);

            bot.spawn();
        }
    }
}
