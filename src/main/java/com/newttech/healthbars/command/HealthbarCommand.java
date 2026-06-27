package com.newttech.healthbars.command;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.gui.HealthbarMenu;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class HealthbarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            new HealthbarMenu(p).open();
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            Healthbars hb = Healthbars.getInstance();

            hb.reloadConfig();
            hb.getConfigManager().reload();
            hb.getTagManager().reload();
            hb.getBlacklistManager().reload();

            hb.getHealthbarManager().cleanupAll();

            p.sendMessage("§aReloaded Healthbars.");
        }

        return true;
    }
}
