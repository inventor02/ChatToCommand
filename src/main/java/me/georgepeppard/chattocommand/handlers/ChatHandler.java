package me.georgepeppard.chattocommand.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.georgepeppard.chattocommand.ChatToCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    private ChatToCommand plugin;

    public ChatHandler(ChatToCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();

        if(plugin.getTriggers().containsKey(message)) {
            String command = plugin.getTriggers().get(message);

            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                command = PlaceholderAPI.setPlaceholders(event.getPlayer(), command);
            }

            if(plugin.getConfig().getBoolean("log", true)) {
                plugin.getLogger().info("[TRIGGER] " + event.getPlayer().getName() + ": " + command + " (" + message + ")");
            }

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

            event.setCancelled(plugin.getConfig().getBoolean("cancel-chat", true));
        }
    }
}
