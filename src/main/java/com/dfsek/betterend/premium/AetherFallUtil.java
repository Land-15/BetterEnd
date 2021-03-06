package com.dfsek.betterend.premium;

import com.dfsek.betterend.config.WorldConfig;
import com.dfsek.betterend.world.EndBiomeGrid;
import com.dfsek.betterend.world.EndChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AetherFallUtil {
    private AetherFallUtil() {
    }

    public static void init(Plugin plugin) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player p : plugin.getServer().getOnlinePlayers()) {
                if(p.getWorld().getGenerator() instanceof EndChunkGenerator
                        && p.getLocation().getY() < 0 && (WorldConfig.fromWorld(p.getWorld()).fallToOverworldEverywhere
                        || (EndBiomeGrid.fromWorld(p.getWorld()).getBiome(p.getLocation()).isAether() && WorldConfig.fromWorld(p.getWorld()).fallToOverworldAether)))
                    p.teleport(new Location(Bukkit.getWorlds().get(0), p.getLocation().getX(), p.getWorld().getMaxHeight(), p.getLocation().getZ()));
            }
        }, 2L, 2L);
    }
}
