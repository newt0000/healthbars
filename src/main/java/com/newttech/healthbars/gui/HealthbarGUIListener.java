package com.newttech.healthbars.gui;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.data.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import com.newttech.healthbars.*;

public class HealthbarGUIListener implements Listener {
    public String pretty(String msg) {
        String value = msg.replace('&','§');
        return value;
    }
    public String read(Boolean state, String value) {
        String color = null;
        String status = null;
        if (state) {
            color = "&a";
            status = "True";
        } else {
            color = "&c";
            status = "False";
        }
        return pretty(value +" | "+color + status);
    }
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
                Boolean pvhbh = true;
                if (s.showHostile) {pvhbh = true;}else {pvhbh = false;}
                String col = read(pvhbh, "&a[Healthbars] healthbar passive rendering set to ");
                p.sendMessage(col);
                // Per-player cleanup: only clear entities near this player so
                // other players' bars are not affected.
                plugin.getHealthbarManager().cleanupForPlayer(p);
                new HealthbarMenu(p).open();
            }

            case SHEEP_SPAWN_EGG -> {
                s.showPassive = !s.showPassive;
                Boolean pvhb = true;
                if (s.showPassive) {pvhb = true;}else {pvhb = false;}
                String col = read(pvhb, "&a[Healthbars] healthbar passive rendering set to ");


                p.sendMessage(col);
                plugin.getHealthbarManager().cleanupForPlayer(p);
                new HealthbarMenu(p).open();
            }

            case COMPASS -> {
                double[] distances = plugin.getConfigManager().guiDistances;

                int next = 0;
                for (int i = 0; i < distances.length; i++) {
                    if (distances[i] == s.renderDistance) {
                        next = (i + 1) % distances.length;
                        p.sendMessage(pretty("&b[Healthbars] &eRendering distance for healthbars set to &6"+s.renderDistance));
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
