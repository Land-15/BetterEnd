package org.polydev.gaea.generation;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.polydev.gaea.biome.Biome;
import org.polydev.gaea.math.ChunkInterpolator;
import org.polydev.gaea.math.FastNoise;
import org.polydev.gaea.profiler.ProfileFuture;
import org.polydev.gaea.profiler.WorldProfiler;

import java.util.List;
import java.util.Random;

public abstract class GaeaChunkGenerator extends ChunkGenerator {
    private FastNoise gen;
    private ChunkInterpolator interp;
    private WorldProfiler profiler;
    private boolean isProfiling = false;

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
        ProfileFuture total = measure("TotalChunkGenTime");
        if (gen == null) {
            gen = new FastNoise((int) world.getSeed());
            gen.setNoiseType(FastNoise.NoiseType.SimplexFractal);
            gen.setFractalOctaves(getNoiseOctaves(world));
            gen.setFrequency(getNoiseFrequency(world));
        }
        interp = new ChunkInterpolator(chunkX, chunkZ, this.getBiomeGrid(world), gen);
        ProfileFuture base = measure("ChunkBaseGenTime");
        ChunkData chunk = generateBase(world, random, chunkX, chunkZ, gen);
        if(base != null) base.complete();
        for(GenerationPopulator g : getGenerationPopulators(world)) {
            chunk = g.populate(world, chunk, random, chunkX, chunkZ);
        }
        ProfileFuture biomeProfile = measure("BiomeSetTime");
        for(byte x = 0; x < 4; x++) {
            for (byte z = 0; z < 4; z++) {
                biome.setBiome(x, z, getBiomeGrid(world).getBiome((chunkX << 4) + x, (chunkZ << 4) + z).getVanillaBiome());
            }
        }
        if(biomeProfile != null) biomeProfile.complete();
        if(total != null) total.complete();
        return chunk;
    }

    public double getInterpolatedNoise(byte x, byte z) {
        return this.interp.getNoise(x, z);
    }

    public void attachProfiler(WorldProfiler p) {
        this.profiler = p;
        isProfiling = true;
    }

    private ProfileFuture measure(String id) {
        if(profiler != null) return profiler.measure(id);
        return null;
    }

    public abstract ChunkData generateBase(@NotNull World world, @NotNull Random random, int x, int z, FastNoise noise);
    public abstract int getNoiseOctaves(World w);
    public abstract float getNoiseFrequency(World w);
    public abstract List<GenerationPopulator> getGenerationPopulators(World w);
    public abstract org.polydev.gaea.biome.BiomeGrid<? extends Biome> getBiomeGrid(World w);
}
