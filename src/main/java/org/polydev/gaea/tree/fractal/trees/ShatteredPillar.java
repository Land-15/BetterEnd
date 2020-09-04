package org.polydev.gaea.tree.fractal.trees;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.polydev.gaea.tree.fractal.FractalTree;

import java.util.Random;

public class ShatteredPillar extends FractalTree {
    /**
     * Instantiates a TreeGrower at an origin location.
     *
     * @param origin - The origin location.
     * @param random - The random object to use whilst generating the tree.
     */
    public ShatteredPillar(Location origin, Random random) {
        super(origin, random);
    }

    /**
     * Grows the tree in memory. Intended to be invoked from an async thread.
     */
    @Override
    public void grow() {
        int h = super.getRandom().nextInt(5)+8;
        int max = h;
        int[] crystalLoc;
        for(int i = -h; i < h; i++) setBlock(super.getOrigin().clone().add(0,i,0), Material.OBSIDIAN);
        h = h + (getRandom().nextBoolean() ? getRandom().nextInt(3)+1 : -(getRandom().nextInt(3)+1));
        if(h > max) {
            max = h;
            crystalLoc = new int[] {1,0};
        }
        for(int i = -h; i < h; i++) setBlock(super.getOrigin().clone().add(1,i,0), Material.OBSIDIAN);
        h = h + (getRandom().nextBoolean() ? getRandom().nextInt(3)+1 : -(getRandom().nextInt(3)+1));
        if(h > max) {
            max = h;
            crystalLoc = new int[] {0,1};
        }
        for(int i = -h; i < h; i++) setBlock(super.getOrigin().clone().add(0,i,1), Material.OBSIDIAN);
        h = h + (getRandom().nextBoolean() ? getRandom().nextInt(3)+1 : -(getRandom().nextInt(3)+1));
        if(h > max) {
            max = h;
            crystalLoc = new int[] {1,1};
        }
        for(int i = -h; i < h; i++) setBlock(super.getOrigin().clone().add(1,i,1), Material.OBSIDIAN);
    }
}
