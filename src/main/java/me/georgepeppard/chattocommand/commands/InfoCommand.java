package me.georgepeppard.chattocommand.commands;

import me.georgepeppard.chattocommand.ChatToCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCommand implements CommandExecutor {

    private ChatToCommand plugin;

    public InfoCommand(ChatToCommand plugin) {
        plugin.getCommand("chattocommand").setExecutor(this);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "ChatToCommand" + ChatColor.GRAY + " version " + plugin.getDescription().getVersion() + " by " + String.join(", ", plugin.getDescription().getAuthors()) + ".");
        sender.sendMessage(ChatColor.GREEN + "" + plugin.getTriggers().size() + " triggers loaded:");
        sender.sendMessage(ChatColor.GRAY + "'" + String.join("', '", plugin.getTriggers().keySet()) + "'");

        return true;
    }
}
