package io.github.caojohnny.stresskitexample.util;

import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.*;

public enum SkepMessages {
    NO_PERMISSION(RED + "You do not have permission to do that!"),
    START_TEST(GREEN + "Started example stress test!"),
    HELP_END(YELLOW + "In order to end this test, type /skep again!"),
    SKEP_END(GREEN + "Ended the example stress test!");

    private final String message;

    SkepMessages(String message) {
        this.message = AQUA + "SKEP" + YELLOW + ": " + message;
    }

    public void sendTo(CommandSender sender) {
        sender.sendMessage(this.message);
    }

    public String getMessage() {
        return this.message;
    }
}
