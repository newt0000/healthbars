package com.newttech.healthbars.data;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.manager.ConfigManager;

public class Settings {

    public boolean showHostile;
    public boolean showPassive;
    public double  renderDistance;

    /** Initialise from config defaults so new players match server settings. */
    public Settings() {
        ConfigManager cfg = Healthbars.getInstance().getConfigManager();
        showHostile    = cfg.defaultShowHostile;
        showPassive    = cfg.defaultShowPassive;
        renderDistance = cfg.defaultRenderDistance;
    }
}
