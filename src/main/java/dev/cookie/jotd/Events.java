package dev.cookie.jotd;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean isEnabled = Main.config.welcomeMessageEnabled();

        if (isEnabled) {
            Player player = event.getPlayer();
            String string = Main.config.welcomeMessageString();
            String formatted = PlaceholderAPI.setPlaceholders(player, string);

            player.sendMessage(formatted);
        }
    }
}
