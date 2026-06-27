package com.newttech.healthbars;

import com.newttech.healthbars.manager.*;
import com.newttech.healthbars.task.HealthbarTask;
import org.bukkit.plugin.java.JavaPlugin;
import com.newttech.healthbars.manager.*;

public class Healthbars extends JavaPlugin {

    private static Healthbars instance;

    private ConfigManager configManager;
    private EntityTagManager tagManager;
    private HealthbarManager healthbarManager;
    private EntityBlacklistManager blacklistManager;

    @Override
    public void onEnable(){

        instance = this;
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        tagManager = new EntityTagManager(this);
        healthbarManager = new HealthbarManager(this);
        blacklistManager = new EntityBlacklistManager();

        getServer().getPluginManager().registerEvents(
                new com.newttech.healthbars.gui.HealthbarGUIListener(),
                this
        );

        new HealthbarTask(healthbarManager)
                .runTaskTimer(this,0L,configManager.updateInterval);
    }

    public static Healthbars getInstance(){ return instance; }

    public ConfigManager getConfigManager(){ return configManager; }
    public EntityTagManager getTagManager(){ return tagManager; }
    public HealthbarManager getHealthbarManager(){ return healthbarManager; }
    public EntityBlacklistManager getBlacklistManager(){ return blacklistManager; }
}