package com.newttech.healthbars.task;

import com.newttech.healthbars.manager.HealthbarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import org.bukkit.scheduler.BukkitRunnable;

public class HealthbarTask extends BukkitRunnable {

    private final HealthbarManager manager;

    public HealthbarTask(HealthbarManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                if (entity instanceof LivingEntity living) {
                    manager.update(player, living);
                }
            }
        }
    }
}