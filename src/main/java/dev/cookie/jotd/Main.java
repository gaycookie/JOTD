package dev.cookie.jotd;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.BoostMultiplier;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import dev.cookie.jotd.cmds.MainCmd;
import dev.cookie.jotd.cmds.MainTab;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static Main instance;
    public static Logger logger = Bukkit.getServer().getLogger();

    public static Storage storage;
    public static Config config;

    @Override
    public void onEnable() {
        instance = this;
        storage = new Storage();
        config = new Config();

        if (Bukkit.getPluginManager().getPlugin("Jobs") == null) {
            logger.severe("Could not find Jobs Reborn! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.severe("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        new Placeholders().register();
        getServer().getPluginManager().registerEvents(new Events(), this);
        Objects.requireNonNull(getCommand("jotd")).setExecutor(new MainCmd());
        Objects.requireNonNull(getCommand("jotd")).setTabCompleter(new MainTab());

        if (storage.getCurrentJob() == null) {
            generateNewJotd();
        }

        activateJotdBoost();
        logger.info(MessageFormat.format("The job of the day is {0} with a {1}% boost.", Main.storage.getCurrentJob(), Main.storage.getCurrentBoost()));
    }

    public static void generateNewJotd() {
        List<Job> jobs = Jobs.getJobs();
        Job randomJob = jobs.get(new Random().nextInt(jobs.size()));
        int randomBoost = new Random().nextInt(config.boostsAmountMin(), config.boostsAmountMax() + 1);

        if (storage.getCurrentJob() == null) {
            storage.setCurrentJob(randomJob.getName());
            storage.setCurrentBoost(randomBoost);
            storage.saveStorage();
        } else {
            storage.setPreviousJob(storage.getCurrentJob());
            storage.setPreviousBoost(storage.getCurrentBoost());
            storage.setCurrentJob(randomJob.getName());
            storage.setCurrentBoost(randomBoost);
            storage.saveStorage();
        }
    }

    public static void activateJotdBoost() {
        BoostMultiplier boost = new BoostMultiplier();
        boost.add(CurrencyType.EXP, (double) storage.getCurrentBoost() / 100);
        boost.add(CurrencyType.MONEY, (double) storage.getCurrentBoost() / 100);
        boost.add(CurrencyType.POINTS, (double) storage.getCurrentBoost() / 100);

        if (storage.getPreviousJob() != null) Jobs.getJob(storage.getPreviousJob()).setBoost(null);
        if (storage.getCurrentJob() != null) Jobs.getJob(storage.getCurrentJob()).setBoost(boost);
    }

    @Override
    public void onDisable() {
        if (storage.getPreviousJob() != null) Jobs.getJob(storage.getPreviousJob()).setBoost(null);
        if (storage.getCurrentJob() != null) Jobs.getJob(storage.getCurrentJob()).setBoost(null);
    }
}
