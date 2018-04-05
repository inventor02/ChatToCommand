package me.georgepeppard.chattocommand.triggers;

import java.util.List;

public class Trigger {

    private String name;
    private String command;
    private String world;
    private String permission;
    private List<String> aliases;

    public Trigger(String name, String command, String world, String permission, List<String> aliases) {
        this.name = name;
        this.command = command;
        this.world = world;
        this.permission = permission;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getWorld() {
        return world;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public boolean hasWorld() {
        return world != null;
    }

    public boolean hasPermission() {
        return permission != null;
    }
}
