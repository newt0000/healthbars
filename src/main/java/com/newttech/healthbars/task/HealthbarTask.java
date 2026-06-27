package com.newttech.healthbars.task;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.data.Settings;
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
        double globalMax = Healthbars.getInstance().getConfigManager().maxDistance;

        for (Player player : Bukkit.getOnlinePlayers()) {
            Settings s = Healthbars.getInstance().getSettingsManager().get(player);
            double radius = Math.min(s.renderDistance, globalMax);

            for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                if (entity instanceof LivingEntity living) {
                    manager.update(player, living);
                }
            }
        }
    }
}
