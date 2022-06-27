package dev.cookie.jotd;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "jotd";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GayCookie";
    }

    @Override
    public @NotNull String getVersion() {
        return Main.instance.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("current_job")) {
            return Main.storage.getCurrentJob();
        }

        if (params.equalsIgnoreCase("current_boost")) {
            return String.valueOf(Main.storage.getCurrentBoost());
        }

        if (params.equalsIgnoreCase("previous_job")) {
            return Main.storage.getPreviousJob();
        }

        if (params.equalsIgnoreCase("previous_boost")) {
            return String.valueOf(Main.storage.getPreviousBoost());
        }

        return null;
    }
}
