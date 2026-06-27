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
        if (!e.getView().getTitle().equals("§8Healthbars Settings")) return;

        e.setCancelled(true);

        Settings s = Healthbars.getInstance().getSettingsManager().get(p);

        if (e.getCurrentItem() == null) return;

        Material type = e.getCurrentItem().getType();

        switch (type) {

            case ZOMBIE_HEAD -> {

                s.showHostile = !s.showHostile;

                Healthbars.getInstance()
                        .getHealthbarManager()
                        .cleanupAllHealthbars();

                new HealthbarMenu(p).open();
            }

            case SHEEP_SPAWN_EGG -> {

                s.showPassive = !s.showPassive;

                Healthbars.getInstance()
                        .getHealthbarManager()
                        .cleanupAllHealthbars();

                new HealthbarMenu(p).open();
            }
            case REDSTONE -> {

                Healthbars plugin = Healthbars.getInstance();

                plugin.reloadConfig();

                plugin.getBlacklistManager().reload();
                plugin.getTagManager().reload();

                plugin.getHealthbarManager().cleanupAllHealthbars();

                p.sendMessage("§aHealthbars configuration reloaded.");

                new HealthbarMenu(p).open();
            }
            case COMPASS -> {

                var config = Healthbars.getInstance().getConfig();

                double[] distances = {
                        config.getDouble("gui.distances.low"),
                        config.getDouble("gui.distances.medium"),
                        config.getDouble("gui.distances.normal"),
                        config.getDouble("gui.distances.max")
                };

                int next = 0;

                for (int i = 0; i < distances.length; i++) {
                    if (distances[i] == s.renderDistance) {
                        next = (i + 1) % distances.length;
                        break;
                    }
                }

                s.renderDistance = distances[next];

                Healthbars.getInstance()
                        .getHealthbarManager()
                        .cleanupAllHealthbars();

                new HealthbarMenu(p).open();
            }

            case BARRIER -> p.closeInventory();
        }
    }
}