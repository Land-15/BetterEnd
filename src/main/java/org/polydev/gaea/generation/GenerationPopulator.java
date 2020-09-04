package org.polydev.gaea.generation;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public abstract class GenerationPopulator {
    public abstract ChunkGenerator.ChunkData populate(World world, ChunkGenerator.ChunkData chunk, Random r, int chunkX, int chunkZ);
}
