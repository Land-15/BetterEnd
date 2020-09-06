package org.polydev.gaea.world.carving;


import org.bukkit.util.Vector;

import java.util.Random;

public class RavineCarver extends Carver {
    private final int chance;
    public RavineCarver(int chance, int minY, int maxY) {
        super(minY, maxY);
        this.chance = chance;
    }

    @Override
    public Worm getWorm(long seed, Vector l) {
        return new RavineWorm(new Random(seed+1).nextInt(40) + 30, new Random(seed), l);
    }

    @Override
    public boolean isChunkCarved(Random r) {
        return r.nextInt(100) < chance;
    }

    public static class RavineWorm extends Worm {
        private final Vector direction;
        private double runningRadius;
        public RavineWorm(int length, Random r, Vector origin) {
            super(length, r, origin);
            runningRadius = (r.nextDouble()/2+0.5)*4;
            direction = new Vector(r.nextDouble()-0.5D, 0, r.nextDouble()-0.5D).normalize();
        }

        @Override
        public void step() {
            setRadius(new int[] {(int) runningRadius, (int) runningRadius*4, (int) runningRadius});
            runningRadius += (getRandom().nextDouble()-0.5)/8;
            runningRadius = Math.min(runningRadius, 6);
            direction.rotateAroundX(Math.toRadians(getRandom().nextDouble()/2));
            direction.rotateAroundY(Math.toRadians(getRandom().nextDouble()*3));
            direction.rotateAroundZ(Math.toRadians(getRandom().nextDouble()/2));
            getRunning().add(direction);
        }
    }
}
