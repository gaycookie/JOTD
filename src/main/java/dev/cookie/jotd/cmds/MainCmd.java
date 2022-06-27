package dev.cookie.jotd.cmds;

import dev.cookie.jotd.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class MainCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§e--------------->> §6JOTD Help§e <<---------------");
            sender.sendMessage("§6/jotd current §fShows the current Job of the Day boost.");
            if (sender.hasPermission("jotd.admin")) sender.sendMessage("§6/jotd generate §fGenerate a new random Job of the Day boost.");
            sender.sendMessage("§6/jotd previous §fShows the previous Job of the Day boost.");
            sender.sendMessage("§e-------------------------------------------");
            return true;
        }

        if (args[0].equalsIgnoreCase("current")) {
            sender.sendMessage(MessageFormat.format("§eThe Job of the Day is §a{0} §ewith a §a{1}% §eboost.", Main.storage.getCurrentJob(), Main.storage.getCurrentBoost()));
            return true;
        }

        if (args[0].equalsIgnoreCase("previous")) {
            sender.sendMessage(MessageFormat.format("§eThe previous Job of the Day was §a{0} §ewith a §a{1}% §eboost.", Main.storage.getPreviousJob(), Main.storage.getPreviousBoost()));
            return true;
        }

        if (sender.hasPermission("jotd.admin")) {
            if (args[0].equalsIgnoreCase("generate")) {
                Main.generateNewJotd();
                Main.activateJotdBoost();
                sender.sendMessage(MessageFormat.format("§eThe new Job of the Day is §a{0} §ewith a §a{1}% §eboost.", Main.storage.getCurrentJob(), Main.storage.getCurrentBoost()));
                return true;
            }
        }

        return true;
    }
}
