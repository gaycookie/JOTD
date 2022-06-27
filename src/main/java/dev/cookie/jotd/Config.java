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

    public boolean welcomeMessageEnabled() {
        return configYml.getBoolean("welcome_message.enabled", false);
    }

    public String welcomeMessageString() {
        return configYml.getString("welcome_message.message", "§eThe Job of the Day is §a%jotd_current_job% §ewith a §a%jotd_current_boost%% §eboost.");
    }

    public int boostsAmountMin() {
        return configYml.getInt("boosts_amounts.min", 10);
    }

    public int boostsAmountMax() {
        return configYml.getInt("boosts_amounts.max", 50);
    }

    public List<String> excludedJobs() {
        return configYml.getStringList("excluded_jobs");
    }
}
