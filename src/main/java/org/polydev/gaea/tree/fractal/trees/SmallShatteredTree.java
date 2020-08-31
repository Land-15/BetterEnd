package org.polydev.gaea.tree.fractal.trees;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.polydev.gaea.math.ProbabilityCollection;
import org.polydev.gaea.tree.fractal.FractalTree;
import org.polydev.gaea.tree.fractal.TreeGeometry;

import java.util.Random;

public class SmallShatteredTree extends FractalTree {
    private final TreeGeometry geo;
    private final ProbabilityCollection<Material> bark = new ProbabilityCollection<Material>()
            .add(Material.OBSIDIAN, 1)
            .add(Material.BLACK_CONCRETE, 1);
    private final ProbabilityCollection<Material> leaves = new ProbabilityCollection<Material>()
            .add(Material.PURPLE_STAINED_GLASS, 1)
            .add(Material.MAGENTA_STAINED_GLASS, 1);
    /**
     * Instantiates a TreeGrower at an origin location.
     *
     * @param origin - The origin location.
     * @param random - The random object to use whilst generating the tree.
     */
    public SmallShatteredTree(Location origin, Random random) {
        super(origin, random);
        geo = new TreeGeometry(this);
    }

    /**
     * Grows the tree in memory. Intended to be invoked from an async thread.
     */
    @Override
    public void grow() {
        growBranch(super.getOrigin().clone(), new Vector(super.getRandom().nextInt(5)-2, super.getRandom().nextInt(3)+4, super.getRandom().nextInt(5)-2), 1.5, 0);

    }

    private void growBranch(Location l1, Vector diff, double d1, int recursions) {
        if(recursions > 2) {
            geo.generateSphere(l1, leaves, 1+super.getRandom().nextInt(2)+(3-recursions), false);
            return;
        }
        if(diff.getY() < 0) diff.rotateAroundAxis(TreeGeometry.getPerpendicular(diff.clone()).normalize(), Math.PI);
        System.out.println(Thread.currentThread() + " Recursion " + recursions);
        int d = (int) diff.length();
        for(int i = 0; i < d; i++) {
            geo.generateSphere(l1.clone().add(diff.clone().multiply((double)i/d)), bark, Math.max((int) d1, 0), true);
        }
        double runningAngle = (double) 45 / (recursions + 1);
        growBranch(l1.clone().add(diff), diff.clone().multiply(0.95).rotateAroundX(Math.toRadians(runningAngle + getNoise())).rotateAroundZ(Math.toRadians(getNoise())),
                d1-1, recursions+1);
        growBranch(l1.clone().add(diff), diff.clone().multiply(0.95).rotateAroundX(Math.toRadians(-runningAngle + getNoise())).rotateAroundZ(Math.toRadians(getNoise())),
                d1-1, recursions+1);
        growBranch(l1.clone().add(diff), diff.clone().multiply(0.95).rotateAroundZ(Math.toRadians(runningAngle + getNoise())).rotateAroundX(Math.toRadians(getNoise())),
                d1 - 1, recursions + 1);
        growBranch(l1.clone().add(diff), diff.clone().multiply(0.95).rotateAroundZ(Math.toRadians(-runningAngle + getNoise())).rotateAroundX(Math.toRadians(getNoise())),
                d1 - 1, recursions + 1);
    }

    private int getNoise() {
        return super.getRandom().nextInt(90)-45;
    }
}
