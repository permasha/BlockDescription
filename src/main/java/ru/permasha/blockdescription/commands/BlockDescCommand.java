package ru.permasha.blockdescription.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BlockDescCommand implements CommandExecutor {

    public BlockDescCommand(JavaPlugin plugin) {
        this.init(plugin);
    }

    private void init(JavaPlugin plugin) {
        plugin.getCommand("blockdesc").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {
        return true;
    }

}
