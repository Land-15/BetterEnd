package org.polydev.gaea.profiler;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.polydev.gaea.generation.GaeaChunkGenerator;

import java.util.HashMap;
import java.util.Map;

public class WorldProfiler {
    private boolean isProfiling;
    private final Map<String, Measurement> measures = new HashMap<>();
    private final World world;

    public WorldProfiler(World w) {
        if(!(w.getGenerator() instanceof GaeaChunkGenerator)) throw new IllegalArgumentException("Attempted to instantiate profiler on non-Gaea managed world!");
        isProfiling = false;
        this.world = w;
        ((GaeaChunkGenerator) w.getGenerator()).attachProfiler(this);
    }

    public String getResultsFormatted() {
        if(!isProfiling) return "Profiler is not currently running.";
        StringBuilder result = new StringBuilder(ChatColor.GOLD + "Gaea World Profiler Results (Min / Avg / Max / Std Dev): \n");
        for(Map.Entry<String, Measurement> e : measures.entrySet()) {
            result.append(ChatColor.GOLD)
                    .append(e.getKey())
                    .append(": ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().getMin()))
                    .append(ChatColor.GOLD)
                    .append(" / ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().average()))
                    .append(ChatColor.GOLD)
                    .append(" / ")
                    .append(e.getValue().getDataHolder().getFormattedData(e.getValue().getMax()))
                    .append(ChatColor.GOLD)
                    .append(" / ")
                    .append(ChatColor.GREEN)
                    .append((double) Math.round((e.getValue().getStdDev()/1000000)*100D)/100D)
                    .append("ms")
                    .append(ChatColor.GOLD)
                    .append("\n");
        }
        return result.toString();
    }

    public void reset() {
        for(Map.Entry<String, Measurement> e : measures.entrySet()) {
            e.getValue().reset();
        }
    }

    public WorldProfiler addMeasurement(Measurement m, String name) {
        measures.put(name, m);
        return this;
    }

    public boolean setMeasurement(String id, long value) {
        if(isProfiling) measures.get(id).record(value);
        return isProfiling;
    }

    public ProfileFuture measure(String id) {
        if(isProfiling) return measures.get(id).beginMeasurement();
        else return null;
    }

    public void setProfiling(boolean enabled) {
        this.isProfiling = enabled;
    }

    public boolean isProfiling() {
        return isProfiling;
    }

    public World getWorld() {
        return world;
    }
}
