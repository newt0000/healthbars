package com.newttech.healthbars.manager;

import com.newttech.healthbars.Healthbars;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class ConfigManager {

    private final Healthbars plugin;

    // Entity lists
    public List<String> hostile;
    public List<String> passive;

    // Bar characters
    public String full;
    public String half;
    public String empty;

    // Bar colours
    public String highColor;
    public String midHighColor;
    public String midColor;
    public String lowColor;

    // Bar behaviour
    public boolean smoothColor;
    public int maxHearts;       // cap on hearts shown (replaces fixed maxSegments)
    public boolean hideFullHealth;

    // Render / update
    public double maxDistance;
    public long updateInterval;

    // Default player settings (applied when a new player is seen)
    public boolean defaultShowHostile;
    public boolean defaultShowPassive;
    public double defaultRenderDistance;

    // Performance
    public double hardCutoffDistance;
    public int staggerTicks;
    public int maxEntitiesPerPlayer;

    // Bossbar
    public boolean bossbarEnabled;
    public double bossbarThreshold;
    public String bossbarColor;
    public String bossbarStyle;

    // GUI
    public String guiTitle;
    public double[] guiDistances;

    public ConfigManager(Healthbars plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        FileConfiguration c = plugin.getConfig();

        hostile = c.getStringList("entities.hostile.list");
        passive = c.getStringList("entities.passive.list");

        full  = c.getString("bar.full-char",  "❤");
        half  = c.getString("bar.half-char",  "❥");
        empty = c.getString("bar.empty-char", "♡");

        highColor    = translate(c.getString("bar.colors.high",     "&a"));
        midHighColor = translate(c.getString("bar.colors.mid-high", "&e"));
        midColor     = translate(c.getString("bar.colors.mid",      "&6"));
        lowColor     = translate(c.getString("bar.colors.low",      "&c"));

        smoothColor   = c.getBoolean("bar.smooth-color", true);
        maxHearts     = c.getInt("bar.max-segments", 20); // config key kept for compat
        hideFullHealth = c.getBoolean("settings.hide-full-health", false);

        maxDistance    = c.getDouble("settings.max-render-distance", 32.0);
        updateInterval = c.getLong("settings.update-interval", 10);

        defaultShowHostile    = c.getBoolean("settings.default-show-hostile", true);
        defaultShowPassive    = c.getBoolean("settings.default-show-passive", true);
        defaultRenderDistance = c.getDouble("gui.distances.medium", 16.0);

        hardCutoffDistance   = c.getDouble("performance.hard-cutoff-distance", 48.0);
        staggerTicks         = c.getInt("performance.stagger-ticks", 2);
        maxEntitiesPerPlayer = c.getInt("performance.max-entities-per-player", 40);

        bossbarEnabled   = c.getBoolean("bossbar.enabled", true);
        bossbarThreshold = c.getDouble("bossbar.threshold-health", 40.0);
        bossbarColor     = c.getString("bossbar.color", "PURPLE");
        bossbarStyle     = c.getString("bossbar.style", "SEGMENTED_10");

        guiTitle = translate(c.getString("gui.title", "&8Healthbars Settings"));
        guiDistances = new double[]{
            c.getDouble("gui.distances.low",    8),
            c.getDouble("gui.distances.medium", 16),
            c.getDouble("gui.distances.normal", 24),
            c.getDouble("gui.distances.max",    32)
        };
    }

    /** Convert &-codes to §-codes. */
    private String translate(String s) {
        return s == null ? "" : s.replace('&', '§');
    }

    public String highColor()    { return highColor; }
    public String midHighColor() { return midHighColor; }
    public String midColor()     { return midColor; }
    public String lowColor()     { return lowColor; }
}
