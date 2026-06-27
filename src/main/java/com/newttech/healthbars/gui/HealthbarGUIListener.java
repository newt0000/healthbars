package com.newttech.healthbars.gui;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.data.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

public class HealthbarGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // Match the title produced by ConfigManager (already §-translated)
        String expectedTitle = Healthbars.getInstance().getConfigManager().guiTitle;
        if (!e.getView().getTitle().equals(expectedTitle)) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null) return;

        Healthbars plugin   = Healthbars.getInstance();
        Settings   s        = plugin.getSettingsManager().get(p);
        Material   type     = e.getCurrentItem().getType();

        switch (type) {

            case ZOMBIE_HEAD -> {
                s.showHostile = !s.showHostile;
                // Per-player cleanup: only clear entities near this player so
                // other players' bars are not affected.
                plugin.getHealthbarManager().cleanupForPlayer(p);
                new HealthbarMenu(p).open();
            }

            case SHEEP_SPAWN_EGG -> {
                s.showPassive = !s.showPassive;
                plugin.getHealthbarManager().cleanupForPlayer(p);
                new HealthbarMenu(p).open();
            }

            case COMPASS -> {
                double[] distances = plugin.getConfigManager().guiDistances;

                int next = 0;
                for (int i = 0; i < distances.length; i++) {
                    if (distances[i] == s.renderDistance) {
                        next = (i + 1) % distances.length;
                        break;
                    }
                }

                s.renderDistance = distances[next];
                // Clean up entities that are now outside the new (smaller) distance,
                // or refresh so newly-included ones appear on the next tick.
                plugin.getHealthbarManager().cleanupForPlayer(p);
                new HealthbarMenu(p).open();
            }

            case REDSTONE -> {
                plugin.reloadConfig();
                plugin.getConfigManager().reload();
                plugin.getBlacklistManager().reload();
                plugin.getTagManager().reload();
                // Full global wipe on a config reload is intentional — settings
                // may have changed for all entities/players.
                plugin.getHealthbarManager().cleanupAllHealthbars();
                p.sendMessage("§aHealthbars configuration reloaded.");
                new HealthbarMenu(p).open();
            }

            case BARRIER -> p.closeInventory();
        }
    }
}
