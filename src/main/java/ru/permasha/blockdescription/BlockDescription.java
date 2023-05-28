package ru.permasha.blockdescription;

import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.blockdescription.listeners.PlayerMove;

public final class BlockDescription extends JavaPlugin {

    @Override
    public void onEnable() {
        PlayerMove playerMove = new PlayerMove(this);

    }

    @Override
    public void onDisable() {

    }

}
