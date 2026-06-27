package com.newttech.healthbars.command;

import com.newttech.healthbars.Healthbars;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class HealthbarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player p)) return true;

        if(args.length == 0){
            // open GUI (not included here)
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")){

            Healthbars hb = Healthbars.getInstance();

            hb.reloadConfig();
            hb.getConfigManager().reload();
            hb.getTagManager().reload();

            hb.getHealthbarManager().cleanupAll();

            p.sendMessage("§aReloaded Healthbars.");
        }

        return true;
    }
}