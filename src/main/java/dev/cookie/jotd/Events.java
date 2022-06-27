package dev.cookie.jotd;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean isEnabled = Main.config.getShowWelcomeMessage();

        if (isEnabled) {
            Player player = event.getPlayer();
            String string = Main.config.getWelcomeMessage();
            String formatted = PlaceholderAPI.setPlaceholders(player, string);

            player.sendMessage(formatted);
        }
    }
}
