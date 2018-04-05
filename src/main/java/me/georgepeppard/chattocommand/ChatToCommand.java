package me.georgepeppard.chattocommand;

import me.georgepeppard.chattocommand.handlers.ChatHandler;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ChatToCommand extends JavaPlugin {

    private HashMap<String, String> triggers;

    private ChatHandler chatHandler;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);

        initConfigs();

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().info("PlaceholderAPI found.");
        } else {
            getLogger().warning("PlaceholderAPI not found! %...% placeholders WILL NOT work!");
        }

        instHandlers();
        initHandlers();
    }

    private void initConfigs() {
        saveDefaultConfig();

        triggers = new HashMap<>();
        getConfig().getConfigurationSection("triggers").getValues(false).forEach((trigger, command) -> triggers.put(trigger.toLowerCase(), command.toString()));
    }

    private void instHandlers() {
        chatHandler = new ChatHandler(this);
    }

    private void initHandlers() {
        getServer().getPluginManager().registerEvents(chatHandler, this);
    }

    public HashMap<String, String> getTriggers() {
        return triggers;
    }
}
