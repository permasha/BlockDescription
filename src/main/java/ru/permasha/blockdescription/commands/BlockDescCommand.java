package ru.permasha.blockdescription.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.permasha.blockdescription.BlockDescription;

import java.util.HashSet;

public class BlockDescCommand implements CommandExecutor {

    BlockDescription plugin;

    public BlockDescCommand(BlockDescription plugin) {
        this.plugin = plugin;
        this.init(plugin);
    }

    private void init(JavaPlugin plugin) {
        plugin.getCommand("blockdesc").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be player");
            return true;
        }

        HashSet<Material> transparent = new HashSet<>();
        transparent.add(Material.AIR);
        Block block = player.getTargetBlock(transparent, 5);

        Location location = block.getLocation();
        String jsonLoc = plugin.getAttributesManager().toJsonLocation(location);

        if (!plugin.getAttributesManager().isBlockConsistsAttributes(location)) {
            player.sendMessage("Данный блок не содержит атрибутов");
            return true;
        }

        return true;
    }

}
