package io.github.caojohnny.stresskit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class StressKit extends JavaPlugin {
    @Override
    public void onEnable() {
        if (!this.checkOfflineMode()) {
            return;
        }

        this.printUseWarning();
    }

    private boolean checkOfflineMode() {
        boolean serverIsOnlineMode = Bukkit.getOnlineMode();
        if (!serverIsOnlineMode) {
            return true;
        }

        Logger logger = this.getLogger();
        logger.severe("----------------------------------------------------------");
        logger.severe("DETECTED SERVER IN ONLINE MODE");
        logger.severe("----------------------------------------------------------");
        logger.severe("The only way for virtual stresser bots to join the server");
        logger.severe("is if the server didn't attempt to verify their accounts;");
        logger.severe("they are bots, after all, and not real accounts.");
        logger.severe("----------------------------------------------------------");
        logger.severe("These bots run on the server itself, so no external");
        logger.severe("connections are being made. As a matter of fact, it is");
        logger.severe("recommended to temporarily restrict your server to LAN");
        logger.severe("connections only to prevent anyone from joining the server");
        logger.severe("while the stress test is in progress.");
        logger.severe("----------------------------------------------------------");

        Bukkit.getPluginManager().disablePlugin(this);
        return false;
    }

    private void printUseWarning() {
        Logger logger = this.getLogger();
        logger.info("----------------------------------------------------------");
        logger.info("StressKit is a library for developing plugins that stress test");
        logger.info("your server by mimicking the behavior of real players.");
        logger.info("----------------------------------------------------------");
        logger.info("This plugin on its own does not do anything!");
        logger.info("You must install other plugins that utilize this library for");
        logger.info("anything to actually happen.");
        logger.info("----------------------------------------------------------");
        logger.info("The use of StressKit can only be used to model performance");
        logger.info("It is not a replacement for actual real-world testing.");
        logger.info("It is intended to be a tool to help diagnose and predict");
        logger.info("the performance constraints of your server.");
        logger.info("----------------------------------------------------------");
    }
}
