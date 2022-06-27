package dev.cookie.jotd.cmds;

import dev.cookie.jotd.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage("§e--------------->> §6JOTD Help§e <<---------------");
            sender.sendMessage("§6/jotd current §fShows the current Job of the Day boost.");
            if (sender.hasPermission("jotd.admin")) sender.sendMessage("§6/jotd generate §fGenerate a new random Job of the Day boost.");
            sender.sendMessage("§6/jotd previous §fShows the previous Job of the Day boost.");
            sender.sendMessage("§e-------------------------------------------");
            return true;
        }

        if (args[0].equalsIgnoreCase("current")) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(player, Main.config.getCurrentJobMessage()));
            return true;
        }

        if (args[0].equalsIgnoreCase("previous")) {
            if (Main.storage.getPreviousJob() == null) {
                sender.sendMessage(ChatColor.RED + "There is no previous Job of the Day yet.");
                return true;
            }

            sender.sendMessage(PlaceholderAPI.setPlaceholders(player, Main.config.getPreviousJobMessage()));
            return true;
        }

        if (sender.hasPermission("jotd.admin")) {
            if (args[0].equalsIgnoreCase("generate")) {
                Main.generateNewJotd();
                Main.activateJotdBoost();

                sender.sendMessage(PlaceholderAPI.setPlaceholders(player, Main.config.getCurrentJobMessage()));
                return true;
            }
        }

        return true;
    }
}
