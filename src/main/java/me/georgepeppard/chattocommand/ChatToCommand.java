package me.georgepeppard.chattocommand;

import me.georgepeppard.chattocommand.commands.InfoCommand;
import me.georgepeppard.chattocommand.handlers.ChatHandler;
import me.georgepeppard.chattocommand.triggers.Trigger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class ChatToCommand extends JavaPlugin {

    private File configFile, triggersFile;
    private FileConfiguration config, triggersConfig;

    private HashMap<String, Trigger> triggers;

    private ChatHandler chatHandler;

    private InfoCommand infoCommand;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);

        initConfigs();
        loadTriggers();

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().info("PlaceholderAPI found.");
        } else {
            getLogger().warning("PlaceholderAPI not found! %...% placeholders WILL NOT work!");
        }

        initHandlers();
        initCommands();
    }

    private void initConfigs() {
        configFile = new File(getDataFolder(), "config.yml");
        triggersFile = new File(getDataFolder(), "triggers.yml");

        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        if(!triggersFile.exists()) {
            triggersFile.getParentFile().mkdirs();
            saveResource("triggers.yml", false);
        }

        config = new YamlConfiguration();
        triggersConfig = new YamlConfiguration();

        try {
            config.load(configFile);
            triggersConfig.load(triggersFile);
        } catch(IOException | InvalidConfigurationException e) {
            getLogger().severe("Failed to load configuration.");
            e.printStackTrace();
        }
    }

    private void loadTriggers() {
        triggers = new HashMap<>();

        getTriggersConfig().getValues(false).forEach((name, section) -> {
            getLogger().info("Loading trigger: " + name.toLowerCase());

            name = name.toLowerCase();
            ConfigurationSection config = (ConfigurationSection) section;

            String command = config.getString("command", "tell %player_name% IMPROPERLY CONFIGURED TRIGGER!");
            String world = config.getString("world", null);
            String permission = config.getString("permission", null);
            List<String> aliases = config.getStringList("aliases");

            Trigger trigger = new Trigger(name, command, world, permission, aliases);
            triggers.put(name, trigger);

            aliases.forEach(alias -> triggers.put(alias, trigger));
        });

        getLogger().info("Complete! Loaded " + triggers.size() + " triggers.");
    }

    private void initHandlers() {
        chatHandler = new ChatHandler(this);
    }

    private void initCommands() {
        infoCommand = new InfoCommand(this);
    }

    public HashMap<String, Trigger> getTriggers() {
        return triggers;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getTriggersConfig() {
        return triggersConfig;
    }
}
