package ru.permasha.blockdescription;

import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.blockdescription.commands.BlockDescCommand;
import ru.permasha.blockdescription.database.Database;
import ru.permasha.blockdescription.database.SQLite;
import ru.permasha.blockdescription.listeners.PlayerMove;
import ru.permasha.blockdescription.managers.AttributesManager;
import ru.permasha.blockdescription.managers.HologramManager;

public class BlockDescription {

    private JavaPlugin plugin;

    private AttributesManager attributesManager;
    private HologramManager hologramManager;
    private Database database;

    public BlockDescription(JavaPlugin plugin) {
        this.init(plugin);
    }

    private void init(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new PlayerMove(this), plugin);
        plugin.getCommand("blockdesc").setExecutor(new BlockDescCommand(this));
        plugin.saveDefaultConfig();

        attributesManager = new AttributesManager(this);
        hologramManager = new HologramManager(this);

        database = new SQLite(this);
        database.load();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public AttributesManager getAttributesManager() {
        return attributesManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public Database getDatabase() {
        return database;
    }
}
