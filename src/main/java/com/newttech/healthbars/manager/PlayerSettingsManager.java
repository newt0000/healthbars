package com.newttech.healthbars.manager;

import com.newttech.healthbars.data.Settings;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSettingsManager {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Settings> settings = new HashMap<>();

    public PlayerSettingsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Settings get(Player player) {
        return settings.computeIfAbsent(player.getUniqueId(), k -> new Settings());
    }
}