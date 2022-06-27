package dev.cookie.jotd.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        ArrayList<String> commands = new ArrayList<>();
        commands.add("current");
        commands.add("previous");

        if (sender.hasPermission("jotd.admin")) {
            commands.add("generate");
        }

        return commands;
    }
}
