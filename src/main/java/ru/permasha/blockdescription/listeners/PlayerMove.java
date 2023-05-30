package ru.permasha.blockdescription.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.blockdescription.objects.LocationModel;

public class PlayerMove implements Listener {

    public PlayerMove(JavaPlugin plugin) {
        this.init(plugin);
    }

    private void init(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();


    }

}
