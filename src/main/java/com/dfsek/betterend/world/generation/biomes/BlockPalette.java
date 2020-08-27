package com.dfsek.betterend.world.generation.biomes;

import com.dfsek.betterend.ProbabilityCollection;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representation of a "slice" of the world.
 * Used to get a section of blocks, based on the depth at which they are found.
 */
public class BlockPalette {
    private final List<PaletteLayer> pallet = new ArrayList<>();
    private final Random random;

    /**
     * Constructs a palette from the given Random object
     * @param random - The random object to use.
     */
    public BlockPalette(Random random) {
        this.random = random;
    }

    /**
     * Adds a material to the palette, for a number of layers.
     * @param m - The material to add to the palette.
     * @param layers - The number of layers the material occupies.
     * @return - BlockPalette instance for chaining.
     */
    public BlockPalette add(Material m, int layers) {
        pallet.add(new PaletteLayer(new ProbabilityCollection<Material>().add(m, 1), layers+(pallet.size() == 0 ? 0 : pallet.get(pallet.size()-1).getLayers())));
        return this;
    }

    /**
     * Adds a ProbabilityCollection to the palette, for a number of layers.
     * @param m - The ProbabilityCollection to add to the palette.
     * @param layers - The number of layers the material occupies.
     * @return - BlockPalette instance for chaining.
     */
    public BlockPalette add(ProbabilityCollection<Material> m, int layers) {
        int l = 0;
        for(PaletteLayer p : pallet) {
            l+=p.getLayers();
        }
        pallet.add(new PaletteLayer(m, layers+l));
        return this;
    }

    /**
     * Fetches a material from the palette, at a given layer.
     * @param layer - The layer at which to fetch the material.
     * @return - Material - The material fetched.
     */
    public Material get(int layer) {
        for(PaletteLayer p : pallet) {
            if(layer < p.getLayers()) return p.getCollection().get(random);
        }
        return pallet.get(pallet.size()-1).getCollection().get(random);
    }
}