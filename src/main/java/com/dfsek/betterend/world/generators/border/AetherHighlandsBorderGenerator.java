package com.dfsek.betterend.world.generators.border;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.polydev.gaea.biome.Generator;
import org.polydev.gaea.math.FastNoiseLite;
import org.polydev.gaea.math.ProbabilityCollection;
import org.polydev.gaea.world.palette.Palette;
import org.polydev.gaea.world.palette.RandomPalette;

import java.util.Random;


public class AetherHighlandsBorderGenerator extends Generator {
    @Override
    public boolean useMinimalInterpolation() {
        return true;
    }
    private final Palette<BlockData> palette;

    public AetherHighlandsBorderGenerator() {
        super();
        this.palette = new RandomPalette<BlockData>(new Random(2403))
                .add(new ProbabilityCollection<BlockData>()
                        .add(Material.GRASS_BLOCK.createBlockData(), 75)
                        .add(Material.COARSE_DIRT.createBlockData(), 5)
                        .add(Material.GRAVEL.createBlockData(), 7)
                        .add(Material.PODZOL.createBlockData(), 13), 1)
                .add(Material.DIRT.createBlockData(), 2)
                .add(Material.STONE.createBlockData(), 1);
    }

    @Override
    public double getNoise(FastNoiseLite gen, World w, int x, int z) {
        return gen.getNoise(x, z) * 1.2;
    }

    @Override
    public double getNoise(FastNoiseLite fastNoise, World w, int i, int i1, int i2) {
        return getNoise(fastNoise, w, i, i2);
    }

    @Override
    public Palette<BlockData> getPalette(int y) {
        return this.palette;
    }
}
