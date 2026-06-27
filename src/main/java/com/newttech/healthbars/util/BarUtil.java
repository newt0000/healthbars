package com.newttech.healthbars.util;

import com.newttech.healthbars.Healthbars;
import com.newttech.healthbars.manager.ConfigManager;

public class BarUtil {

    /**
     * Builds a heart-based health bar where each segment = 1 Minecraft heart (2 HP).
     * The bar length matches the entity's actual max hearts, capped at cfg.maxHearts
     * so mobs with huge HP pools (iron golems, bosses) don't produce absurdly long bars.
     *
     * Full char  = full heart  (≥ 2 HP remaining for this segment)
     * Half char  = half heart  (0 < HP remaining < 2)
     * Empty char = empty heart (no HP left for this segment)
     */
    public static String buildBar(double hp, double max) {

        ConfigManager cfg = Healthbars.getInstance().getConfigManager();

        String full  = cfg.full;
        String half  = cfg.half;
        String empty = cfg.empty;

        // 1 heart = 2 HP in vanilla Minecraft
        int totalHearts = (int) Math.ceil(max / 2.0);
        int displayHearts = Math.min(totalHearts, cfg.maxHearts);

        // Scale hp proportionally if we're capping the display
        double displayHp = (displayHearts == totalHearts)
                ? hp
                : hp * ((double) displayHearts / totalHearts);

        double remaining = displayHp;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < displayHearts; i++) {
            if (remaining >= 2.0) {
                sb.append(full);
            } else if (remaining > 0.0) {
                sb.append(half);
            } else {
                sb.append(empty);
            }
            remaining -= 2.0;
        }

        return sb.toString();
    }

    /**
     * Returns the colour code for the bar based on current HP percentage.
     * If smooth-color is enabled each segment could be coloured individually —
     * that would require buildBar to return a richer structure; for now this
     * returns a single leading colour which is the most common approach.
     */
    public static String color(double hp, double max) {

        double p = hp / max;
        ConfigManager cfg = Healthbars.getInstance().getConfigManager();

        if (p > 0.75) return cfg.highColor();
        if (p > 0.50) return cfg.midHighColor();
        if (p > 0.25) return cfg.midColor();
        return cfg.lowColor();
    }
}
