package com.newttech.healthbars.util;

import com.newttech.healthbars.Healthbars;

public class BarUtil {

    public static String buildBar(double hp, double max){

        var cfg = Healthbars.getInstance().getConfigManager();

        String full = cfg.full;
        String half = cfg.half;
        String empty = cfg.empty;

        int maxSeg = cfg.maxSegments;

        double perSeg = max / maxSeg;
        double remaining = hp;

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<maxSeg;i++){

            if(remaining >= perSeg){
                sb.append(full);
            } else if(remaining > 0){
                sb.append(half);
            } else {
                sb.append(empty);
            }

            remaining -= perSeg;
        }

        return sb.toString();
    }

    public static String color(double hp, double max){

        double p = hp / max;
        var cfg = Healthbars.getInstance().getConfigManager();

        if(p > 0.75) return cfg.highColor();
        if(p > 0.50) return cfg.midHighColor();
        if(p > 0.25) return cfg.midColor();
        return cfg.lowColor();
    }
}