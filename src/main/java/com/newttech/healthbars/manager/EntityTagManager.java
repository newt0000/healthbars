package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.Set;

public class EntityTagManager {

    private final JavaPlugin plugin;

    private final Set<EntityType> hostile = EnumSet.noneOf(EntityType.class);
    private final Set<EntityType> passive = EnumSet.noneOf(EntityType.class);

    public EntityTagManager(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        hostile.clear();
        passive.clear();

        FileConfiguration config = Healthbars.getInstance().getConfig();

        for (String type : config.getStringList("entities.hostile.list")) {
            try {
                hostile.add(EntityType.valueOf(type.toUpperCase()));
            } catch (IllegalArgumentException ex) {
                Healthbars.getInstance().getLogger().warning("Unknown hostile entity: " + type);
            }
        }

        for (String type : config.getStringList("entities.passive.list")) {
            try {
                passive.add(EntityType.valueOf(type.toUpperCase()));
            } catch (IllegalArgumentException ex) {
                Healthbars.getInstance().getLogger().warning("Unknown passive entity: " + type);
            }
        }
    }

    public boolean isHostile(EntityType type) {
        return hostile.contains(type);
    }

    public boolean isPassive(EntityType type) {
        return passive.contains(type);
    }
}
