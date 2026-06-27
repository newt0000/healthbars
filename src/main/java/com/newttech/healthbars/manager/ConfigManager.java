package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class ConfigManager {

    private final Healthbars plugin;

    public List<String> hostile;
    public List<String> passive;

    public String full;
    public String half;
    public String empty;

    public String highColor;
    public String midHighColor;
    public String midColor;
    public String lowColor;

    public int maxSegments;

    public double maxDistance;
    public long updateInterval;

    public ConfigManager(Healthbars plugin){
        this.plugin = plugin;
        reload();
    }

    public void reload(){
        FileConfiguration c = plugin.getConfig();

        hostile = c.getStringList("entities.hostile.list");
        passive = c.getStringList("entities.passive.list");

        full = c.getString("bar.full-char","█");
        half = c.getString("bar.half-char","▌");
        empty = c.getString("bar.empty-char","░");

        highColor = c.getString("bar.colors.high","&a");
        midHighColor = c.getString("bar.colors.mid-high","&e");
        midColor = c.getString("bar.colors.mid","&6");
        lowColor = c.getString("bar.colors.low","&c");

        maxSegments = c.getInt("bar.max-segments",20);

        maxDistance = c.getDouble("settings.max-render-distance",32.0);
        updateInterval = c.getLong("settings.update-interval",10);
    }

    public String highColor(){ return highColor; }
    public String midHighColor(){ return midHighColor; }
    public String midColor(){ return midColor; }
    public String lowColor(){ return lowColor; }
}