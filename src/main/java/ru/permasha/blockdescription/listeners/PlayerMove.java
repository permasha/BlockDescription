package ru.permasha.blockdescription.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.blockdescription.BlockDescription;

public class PlayerMove implements Listener {

    BlockDescription plugin;

    public PlayerMove(BlockDescription plugin) {
        this.plugin = plugin;
        this.init(plugin);
    }

    private void init(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Player player = event.getPlayer();

            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                plugin.getDatabase().getDataCache().keySet().forEach(locStr -> {
                    Location location = plugin.getAttributesManager().fromJsonLocation(locStr);
                    plugin.getHologramManager().showPlayerHologram(player, location);
                });
            }
        });
    }

}
