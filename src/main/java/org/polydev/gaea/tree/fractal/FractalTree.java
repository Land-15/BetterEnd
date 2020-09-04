package org.polydev.gaea.tree.fractal;

import com.dfsek.betterend.config.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.*;
import java.util.function.Supplier;

public abstract class FractalTree {
    private final Map<Location, BlockData> treeAssembler = new HashMap<>();
    private final Location origin;
    private final Random random;
    private final List<Material> replaceable = Arrays.asList(Material.AIR, Material.GRASS_BLOCK, Material.DIRT, Material.STONE, Material.COARSE_DIRT, Material.GRAVEL, Material.PODZOL,
            Material.GRASS, Material.TALL_GRASS, Material.FERN, Material.POPPY, Material.LARGE_FERN, Material.BLUE_ORCHID, Material.AZURE_BLUET);


    /**
     * Instantiates a TreeGrower at an origin location.
     * @param origin - The origin location.
     * @param random - The random object to use whilst generating the tree.
     */
    public FractalTree(Location origin, Random random) {
        this.origin = origin;
        this.random = random;
    }

    /**
     * Gets the raw tree map.
     * @return HashMap&lt;Location, BlockData&gt; - The raw dictionary representation of the tree.
     */
    public Map<Location, BlockData> getTree() {
        return treeAssembler;
    }


    /**
     * Fetches the Random object used to generate the tree.
     * @return Random - The Random object.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Fetches the origin location.
     * @return Location - The origin location specified upon instantiation.
     */
    public Location getOrigin() {
        return origin;
    }

    /**
     * Sets a block in the tree's storage map to a material.
     * @param l - The location to set.
     * @param m - The material to which it will be set.
     */
    public void setBlock(Location l, Material m) {
        treeAssembler.put(l, m.createBlockData());
    }

    /**
     * Grows the tree in memory. Intended to be invoked from an async thread.
     */
    public abstract void grow();

    /**
     * Pastes the tree in the world. Must be invoked from main thread.
     */
    public void plant(boolean doCheck) {
        if(doCheck && !this.getOrigin().getBlock().isPassable()) return;
        if(ConfigUtil.debug) Bukkit.getLogger().info("[" + Thread.currentThread().getName() + "] Planting tree...");
        for(Map.Entry<Location, BlockData> entry : treeAssembler.entrySet()) {
            if(replaceable.contains(entry.getKey().getBlock().getType())) entry.getKey().getBlock().setBlockData(entry.getValue(), false);
        }
    }

    /**
     * Gets the material at the specified block.
     * Returns air if no material has been set.
     * @param l - The location at which to check.
     * @return Material - The material at the specified block.
     */
    public Material getMaterial(Location l) {
        return treeAssembler.getOrDefault(l, Material.AIR.createBlockData()).getMaterial();
    }


}
