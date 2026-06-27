package com.newttech.healthbars.gui;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.data.Settings;
import com.newttech.healthbars.manager.PlayerSettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HealthbarMenu {

    private final Player player;
    private final PlayerSettingsManager settingsManager;

    public HealthbarMenu(Player player) {
        this.player = player;
        this.settingsManager = Healthbars.getInstance().getSettingsManager();
    }

    public void open() {

        Inventory inv = Bukkit.createInventory(null, 27, "§8Healthbars Settings");

        Settings s = settingsManager.get(player);

        // SLOT 10 - Toggle Hostile
        inv.setItem(10, createItem(
                Material.ZOMBIE_HEAD,
                "§cHostile Mobs",
                "§7Currently: " + (s.showHostile ? "§aON" : "§cOFF")
        ));

        // SLOT 11 - Toggle Passive
        inv.setItem(11, createItem(
                Material.SHEEP_SPAWN_EGG,
                "§aPassive Mobs",
                "§7Currently: " + (s.showPassive ? "§aON" : "§cOFF")
        ));

        // SLOT 13 - Distance (Main Control)
        inv.setItem(13, createItem(
                Material.COMPASS,
                "§eRender Distance",
                "§7Current: §f" + s.renderDistance + " blocks"
        ));

        // SLOT 15 - Reload Hint
        inv.setItem(15, createItem(
                Material.REDSTONE,
                "§cReload Config",
                "§7Use /hb reload"
        ));

        // SLOT 22 - Close
        inv.setItem(22, createItem(
                Material.BARRIER,
                "§cClose",
                "§7Exit menu"
        ));

        player.openInventory(inv);
    }

    private ItemStack createItem(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        return item;
    }
}