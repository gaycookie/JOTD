package dev.cookie.jotd;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.BoostMultiplier;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.*;
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
        Objects.requireNonNull(getCommand("jotd")).setExecutor(this);
        Objects.requireNonNull(getCommand("jotd")).setTabCompleter(this);

        if (storage.getCurrentJob() == null) {
            generateNewJotd();
        }

        activateJotdBoost();
        logger.info(MessageFormat.format("The job of the day is {0} with a {1}% boost.", Main.storage.getCurrentJob(), Main.storage.getCurrentBoost()));
    }

    @Override
    public void onDisable() {
        if (storage.getPreviousJob() != null) Jobs.getJob(storage.getPreviousJob()).setBoost(null);
        if (storage.getCurrentJob() != null) Jobs.getJob(storage.getCurrentJob()).setBoost(null);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        boolean hasPermission = sender instanceof ConsoleCommandSender || sender.hasPermission("jotd.admin");

        ArrayList<String> commands = new ArrayList<>();
        commands.add("current");
        if (hasPermission) commands.add("generate");
        commands.add("previous");
        if (hasPermission) commands.add("reload");

        return commands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean hasPermission = sender instanceof ConsoleCommandSender || sender.hasPermission("jotd.admin");

        if (args.length == 0) {
            sender.sendMessage("§e--------------->> §6JOTD Help§e <<---------------");
            sender.sendMessage("§6/jotd current §fShows the current Job of the Day boost.");
            if (hasPermission) sender.sendMessage("§6/jotd generate §fGenerate a new random Job of the Day boost.");
            sender.sendMessage("§6/jotd previous §fShows the previous Job of the Day boost.");
            if (hasPermission) sender.sendMessage("§6/jotd reload §fReload the Job of the Day plugin.");
            sender.sendMessage("§e-------------------------------------------");
            return true;
        }

        if (args[0].equalsIgnoreCase("current")) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, Main.config.getCurrentJobMessage()));
            return true;
        }

        if (args[0].equalsIgnoreCase("previous")) {
            if (Main.storage.getPreviousJob() == null) {
                sender.sendMessage(ChatColor.RED + "Oopsie-daisy, there is no previous Job of the Day yet.");
                return true;
            }

            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, Main.config.getPreviousJobMessage()));
            return true;
        }

        if (args[0].equalsIgnoreCase("generate")) {
            if (!hasPermission) {
                sender.sendMessage(ChatColor.RED + "Oopsie-daisy, you're not allowed to use this.");
                return true;
            }

            Main.generateNewJotd();
            Main.activateJotdBoost();
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, Main.config.getCurrentJobMessage()));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!hasPermission) {
                sender.sendMessage(ChatColor.RED + "Oopsie-daisy, you're not allowed to use this.");
                return true;
            }

            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getPluginManager().enablePlugin(this);
            sender.sendMessage(ChatColor.GREEN + "JOTD was successfully reloaded.");
            return true;
        }

        return true;
    }

    public static void generateNewJotd() {
        List<Job> jobs = new LinkedList<>(Jobs.getJobs());

        if (config.getExcludedJobs().size() != 0) {
            config.getExcludedJobs().forEach(jobName -> {
                jobs.removeIf(job -> job.getName().equalsIgnoreCase(jobName));
            });
        }

        Job randomJob = jobs.get(new Random().nextInt(jobs.size()));
        int randomBoost = new Random().nextInt(config.getBoostsAmountMix(), config.getBoostsAmountMax() + 1);

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

        if (config.getIsEXPBoosted()) boost.add(CurrencyType.EXP, (double) storage.getCurrentBoost() / 100);
        if (config.getIsMoneyBoosted()) boost.add(CurrencyType.MONEY, (double) storage.getCurrentBoost() / 100);
        if (config.getIsPointsBoosted()) boost.add(CurrencyType.POINTS, (double) storage.getCurrentBoost() / 100);

        if (storage.getPreviousJob() != null) Jobs.getJob(storage.getPreviousJob()).setBoost(null);
        if (storage.getCurrentJob() != null) Jobs.getJob(storage.getCurrentJob()).setBoost(boost);
    }
}
