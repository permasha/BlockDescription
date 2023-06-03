package ru.permasha.blockdescription.managers;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.permasha.blockdescription.BlockDescription;

public class HologramManager {

    BlockDescription plugin;
    HolographicDisplaysAPI api;

    public HologramManager(BlockDescription plugin) {
        this.plugin = plugin;
        this.api = HolographicDisplaysAPI.get(plugin);
    }

    public void createHologram(Location location) {
        Location formattedLoc = location.add(0.5D, 0.5D, 0.5D);
        Hologram hologram =  api.createHologram(formattedLoc);
        String symbol = plugin.getAttributesManager().getSymbol();
        hologram.getLines().appendText(symbol);
    }

    public void removeHologram(Location location) {
        api.getHolograms().forEach(hologram -> {
            if (location.equals(hologram.getPosition().toLocation())) {
                hologram.delete();
            }
        });
    }

    public Hologram getHologramOnLocation(Location location) {
        for (Hologram hologram : api.getHolograms()) {
            if (location.equals(hologram.getPosition().toLocation())) {
                return hologram;
            }
        }
        return null;
    }

    public void showPlayerHologram(Player player, Location location) {
        Location formattedLoc = location.add(0.5D, 0.5D, 0.5D);
        Hologram hologram = getHologramOnLocation(formattedLoc);
        int radius = plugin.getAttributesManager().getRadius();
        if (hologram != null) {
            if ((location.distance(player.getLocation()) <= radius) && (location.getBlock().getType() != Material.AIR)) {
                hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.VISIBLE);
            } else {
                hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.HIDDEN);
            }
        }
    }

}
