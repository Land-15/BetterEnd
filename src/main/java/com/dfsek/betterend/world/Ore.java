package com.dfsek.betterend.world;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class Ore {
	private final int contChance;
	private final BlockData oreMaterial;

	public Ore(BlockData oreMaterial, int contChance) {
		this.contChance = contChance;
		this.oreMaterial = oreMaterial;
	}

	public int getContChance() {
		return contChance;
	}

	public BlockData getType() {
		return oreMaterial;
	}
}
