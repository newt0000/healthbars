package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.data.Settings;
import com.newttech.healthbars.util.BarUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

public class HealthbarManager {

    private final Healthbars plugin;

    public HealthbarManager(Healthbars plugin) {
        this.plugin = plugin;
    }

    public void update(Player viewer, LivingEntity entity) {

        if (plugin.getBlacklistManager().isBlocked(entity.getType()))
            return;

        Settings s = plugin.getSettingsManager().get(viewer);

        double dist = viewer.getLocation().distance(entity.getLocation());
        if (dist > s.renderDistance) return;

        var tag = plugin.getTagManager();
        boolean hostile = tag.isHostile(entity.getType());
        boolean passive = tag.isPassive(entity.getType());

        if (hostile && !s.showHostile) return;
        if (passive && !s.showPassive) return;
        if (!hostile && !passive) return;

        double hp  = entity.getHealth();
        double max = entity.getAttribute(Attribute.MAX_HEALTH).getValue();

        // Optionally hide bar when entity is at full health
        if (plugin.getConfigManager().hideFullHealth && hp >= max) {
            entity.setCustomName(null);
            entity.setCustomNameVisible(false);
            return;
        }

        String bar = BarUtil.color(hp, max) + BarUtil.buildBar(hp, max);
        entity.setCustomName(bar);
        entity.setCustomNameVisible(true);
    }

    /**
     * Removes healthbar custom names from every living entity in all worlds.
     * Used on plugin reload / disable.
     */
    public void cleanupAll() {
        for (var w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e instanceof LivingEntity le) {
                    le.setCustomName(null);
                    le.setCustomNameVisible(false);
                }
            }
        }
    }

    /**
     * Removes healthbar custom names only from living entities currently near
     * the given player (within their render distance).
     *
     * Called when a player changes a visibility or distance setting so their
     * change takes effect immediately without wiping bars for every other player.
     */
    public void cleanupForPlayer(Player player) {
        Settings s = plugin.getSettingsManager().get(player);
        double radius = Math.min(s.renderDistance, plugin.getConfigManager().maxDistance);

        for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof LivingEntity le) {
                le.setCustomName(null);
                le.setCustomNameVisible(false);
            }
        }
    }

    /** Kept for command/reload call-sites that want a full global wipe. */
    public void cleanupAllHealthbars() {
        cleanupAll();
    }
}
