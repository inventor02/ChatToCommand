package me.georgepeppard.chattocommand.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.georgepeppard.chattocommand.ChatToCommand;
import me.georgepeppard.chattocommand.triggers.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    private ChatToCommand plugin;

    public ChatHandler(ChatToCommand plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        Player player = event.getPlayer();

        if(plugin.getTriggers().containsKey(message)) {
            Trigger trigger = plugin.getTriggers().get(message);
            String command = trigger.getCommand();

            if(trigger.hasPermission() && !player.hasPermission(trigger.getPermission())) {
                if(plugin.getConfig().getBoolean("allow-chat-with-no-permissions", true)) {
                    return;
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this trigger.");
                    event.setCancelled(true);
                    return;
                }
            }

            if(trigger.hasWorld() && !player.getWorld().getName().equalsIgnoreCase(trigger.getWorld())) {
                if(plugin.getConfig().getBoolean("allow-chat-with-incorrect-world", true)) {
                    return;
                } else {
                    player.sendMessage(ChatColor.RED + "You are in the incorrect world to use this trigger.");
                    event.setCancelled(true);
                    return;
                }
            }

            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                command = PlaceholderAPI.setPlaceholders(event.getPlayer(), command);
            }

            if(plugin.getConfig().getBoolean("log", true)) {
                plugin.getLogger().info("[TRIGGER] " + event.getPlayer().getName() + ": " + command + " (" + trigger.getName() + ")");
            }

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

            event.setCancelled(plugin.getConfig().getBoolean("cancel-chat", true));
        }
    }
}
