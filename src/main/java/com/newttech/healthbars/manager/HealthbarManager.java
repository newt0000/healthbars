package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.util.BarUtil;
import org.bukkit.entity.*;
import org.bukkit.attribute.Attribute;

public class HealthbarManager {

    private final Healthbars plugin;

    public HealthbarManager(Healthbars plugin){
        this.plugin = plugin;
    }

    public void update(Player viewer, LivingEntity entity){

        if(plugin.getBlacklistManager().isBlocked(entity.getType()))
            return;

        double dist = viewer.getLocation().distance(entity.getLocation());
        if(dist > plugin.getConfigManager().maxDistance) return;

        var tag = plugin.getTagManager();

        boolean hostile = tag.isHostile(entity.getType());
        boolean passive = tag.isPassive(entity.getType());

        if(!hostile && !passive) return;

        double hp = entity.getHealth();
        double max = entity.getAttribute(Attribute.MAX_HEALTH).getValue();

        String bar = BarUtil.color(hp,max) + BarUtil.buildBar(hp,max);

        entity.setCustomName(bar);
        entity.setCustomNameVisible(true);
    }

    public void cleanupAll(){
        for(var w : org.bukkit.Bukkit.getWorlds()){
            for(Entity e : w.getEntities()){
                if(e instanceof LivingEntity le){
                    le.setCustomName(null);
                    le.setCustomNameVisible(false);
                }
            }
        }
    }
}