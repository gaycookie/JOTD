package dev.cookie.jotd;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {
    private File configFile;
    private YamlConfiguration configYml;

    public Config() {
        configYml = new YamlConfiguration();
        configFile = new File(Main.instance.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Main.instance.saveResource("config.yml", false);
        }

        loadConfig();
    }

    private void loadConfig() {
        try {
            configYml.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getShowWelcomeMessage() {
        return configYml.getBoolean("show_welcome_message", false);
    }

    public String getWelcomeMessage() {
        return configYml.getString("messages.welcome", "§eThe Job of the Day is §a%jotd_current_job% §ewith a §a%jotd_current_boost%% §eboost.");
    }

    public String getCurrentJobMessage() {
        return configYml.getString("messages.current", "§eThe Job of the Day is §a%jotd_current_job% §ewith a §a%jotd_current_boost%% §eboost.");
    }

    public String getPreviousJobMessage() {
        return configYml.getString("messages.previous", "§eThe previous Job of the Day was §a%jotd_previous_job% §ewith a §a%jotd_previous_job%% §eboost.");
    }

    public int getBoostsAmountMix() {
        return configYml.getInt("boosts_amounts.min", 10);
    }

    public int getBoostsAmountMax() {
        return configYml.getInt("boosts_amounts.max", 50);
    }

    public List<String> getExcludedJobs() {
        return configYml.getStringList("excluded_jobs");
    }
}
