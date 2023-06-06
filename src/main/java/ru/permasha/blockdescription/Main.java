package ru.permasha.blockdescription;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    BlockDescription module;

    @Override
    public void onEnable() {
        module = new BlockDescription(this);
    }

    @Override
    public void onDisable() {
        module.getHologramManager().clearHolograms();
    }

}
