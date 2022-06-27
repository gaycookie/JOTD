package dev.cookie.jotd;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Storage {
    private File storageFile;
    private YamlConfiguration storageYml;

    public Storage() {
        storageYml = new YamlConfiguration();
        storageFile = new File(Main.instance.getDataFolder(), "storage.yml");

        if (!storageFile.exists()) {
            storageFile.getParentFile().mkdirs();
            Main.instance.saveResource("storage.yml", false);
        }

        loadStorage();
    }

    private void loadStorage() {
        try {
            storageYml.load(storageFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveStorage() {
        try {
            storageYml.save(storageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurrentJob(String job) {
        storageYml.set("current.job", job);
    }

    public void setCurrentBoost(int boost) {
        storageYml.set("current.boost", boost);
    }

    public void setPreviousJob(String job) {
        storageYml.set("previous.job", job);
    }

    public void setPreviousBoost(int boost) {
        storageYml.set("previous.boost", boost);
    }

    public String getCurrentJob() {
        return storageYml.getString("current.job", null);
    }

    public String getPreviousJob() {
        return storageYml.getString("previous.job", null);
    }

    public int getCurrentBoost() {
        return storageYml.getInt("current.boost", 0);
    }

    public int getPreviousBoost() {
        return storageYml.getInt("previous.boost", 0);
    }
}
