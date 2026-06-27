package com.newttech.healthbars;

import com.newttech.healthbars.command.HealthbarCommand;
import com.newttech.healthbars.manager.*;
import com.newttech.healthbars.task.HealthbarTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Healthbars extends JavaPlugin {

    private static Healthbars instance;

    private ConfigManager configManager;
    private EntityTagManager tagManager;
    private HealthbarManager healthbarManager;
    private EntityBlacklistManager blacklistManager;
    private PlayerSettingsManager settingsManager;
    private HealthbarCommand HealthbarCommand;

    @Override
    public void onEnable(){

        instance = this;
        saveDefaultConfig();

        configManager   = new ConfigManager(this);
        tagManager      = new EntityTagManager(this);
        healthbarManager = new HealthbarManager(this);
        blacklistManager = new EntityBlacklistManager();
        settingsManager  = new PlayerSettingsManager(this);
        Objects.requireNonNull(getCommand("hb")).setExecutor(new HealthbarCommand());
        getServer().getPluginManager().registerEvents(
                new com.newttech.healthbars.gui.HealthbarGUIListener(),
                this
        );

        new HealthbarTask(healthbarManager)
                .runTaskTimer(this, 0L, configManager.updateInterval);
        getLogger().info("[HealthBars] Enabled!\n    __  __           ____  __    ____                 \n" +
                "   / / / /__  ____ _/ / /_/ /_  / __ )____ ___________\n" +
                "  / /_/ / _ \\/ __ `/ / __/ __ \\/ __  / __ `/ ___/ ___/\n" +
                " / __  /  __/ /_/ / / /_/ / / / /_/ / /_/ / /  (__  ) \n" +
                "/_/ /_/\\___/\\__,_/_/\\__/_/ /_/_____/\\__,_/_/  /____/  \n" +
                "                                                      \nBy: Newt_00");
    }
    public String pretty(String msg) {
        String value = msg.replace('&','§');
        return value;
    }
    public static Healthbars getInstance(){ return instance; }

    public ConfigManager getConfigManager()         { return configManager; }
    public EntityTagManager getTagManager()         { return tagManager; }
    public HealthbarManager getHealthbarManager()   { return healthbarManager; }
    public EntityBlacklistManager getBlacklistManager() { return blacklistManager; }
    public PlayerSettingsManager getSettingsManager()   { return settingsManager; }
}
