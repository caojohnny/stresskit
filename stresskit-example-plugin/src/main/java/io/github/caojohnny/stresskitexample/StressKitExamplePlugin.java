package io.github.caojohnny.stresskitexample;

import io.github.caojohnny.stresskit.exec.BotRunner;
import io.github.caojohnny.stresskit.exec.BukkitBotRunner;
import io.github.caojohnny.stresskit.factory.BukkitBotPlayerFactory;
import io.github.caojohnny.stresskit.v1_14_4.factory.BotPlayerFactory;
import io.github.caojohnny.stresskitexample.cmd.SkepCmd;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class StressKitExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.printInfo();

        BotRunner runner = new BukkitBotRunner(this);
        BotPlayerFactory factory = new BukkitBotPlayerFactory(runner);
        factory.init();

        this.getCommand("skep").setExecutor(new SkepCmd(factory));
    }

    private void printInfo() {
        Logger logger = this.getLogger();

        logger.info("------------------------");
        logger.info("StressKitExamplePlugin");
        logger.info("This plugin will begin the stress test");
        logger.info("when you use the /skep command.");
        logger.info("------------------------");
    }
}
