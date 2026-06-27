package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityBlacklistManager {

    private final Set<EntityType> blacklisted = new HashSet<>();

    public EntityBlacklistManager() {
        reload();
    }

    public void reload() {
        blacklisted.clear();

        List<String> list = Healthbars.getInstance()
                .getConfig()
                .getStringList("blacklist.entities");

        for (String entry : list) {
            try {
                blacklisted.add(EntityType.valueOf(entry.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                Healthbars.getInstance().getLogger()
                        .warning("Invalid blacklist entity: " + entry);
            }
        }
    }

    public boolean isBlocked(EntityType type) {
        if (!Healthbars.getInstance().getConfig().getBoolean("blacklist.enabled", true))
            return false;

        return blacklisted.contains(type);
    }
}