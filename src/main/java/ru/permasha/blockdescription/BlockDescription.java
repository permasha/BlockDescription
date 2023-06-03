package ru.permasha.blockdescription;

import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.blockdescription.commands.BlockDescCommand;
import ru.permasha.blockdescription.database.Database;
import ru.permasha.blockdescription.database.SQLite;
import ru.permasha.blockdescription.listeners.PlayerMove;
import ru.permasha.blockdescription.managers.AttributesManager;
import ru.permasha.blockdescription.managers.HologramManager;

public final class BlockDescription extends JavaPlugin {

    private AttributesManager attributesManager;
    private HologramManager hologramManager;
    private Database database;

    @Override
    public void onEnable() {
        PlayerMove playerMove = new PlayerMove(this);
        BlockDescCommand blockDescCommand = new BlockDescCommand(this);
        saveDefaultConfig();
        attributesManager = new AttributesManager(this);
        hologramManager = new HologramManager(this);

        database = new SQLite(this);
        database.load();
    }

    @Override
    public void onDisable() {

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
