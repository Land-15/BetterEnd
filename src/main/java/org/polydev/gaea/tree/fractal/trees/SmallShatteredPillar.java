package org.polydev.gaea.tree.fractal.trees;

import org.bukkit.Location;
import org.bukkit.Material;
import org.polydev.gaea.tree.fractal.FractalTree;

import java.util.Random;

public class SmallShatteredPillar extends FractalTree {
    /**
     * Instantiates a TreeGrower at an origin location.
     *
     * @param origin - The origin location.
     * @param random - The random object to use whilst generating the tree.
     */
    public SmallShatteredPillar(Location origin, Random random) {
        super(origin, random);
    }

    /**
     * Grows the tree in memory. Intended to be invoked from an async thread.
     */
    @Override
    public void grow() {
        int h = super.getRandom().nextInt(5)+5;
        for(int i = -h; i < h; i++) setBlock(super.getOrigin().clone().add(0,i,0).getBlock(), Material.OBSIDIAN);
    }
}
