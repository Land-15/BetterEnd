package com.dfsek.betterend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
	private static List<String> COMMANDS = Arrays.asList("biome", "tpbiome", "version", "reload");
	private static List<String> BIOMES = Arrays.asList("AETHER", "END", "SHATTERED_END", "AETHER_HIGHLANDS", "SHATTERED_FOREST", "VOID", "STARFIELD");
	static {
		if(Main.isPremium()) {
			BIOMES = Arrays.asList("AETHER", "END", "SHATTERED_END", "AETHER_HIGHLANDS", "SHATTERED_FOREST", "AETHER_FOREST", "AETHER_HIGHLANDS_FOREST", "VOID",
					"STARFIELD");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> argList = new ArrayList<>();
		if(args.length == 1) {
			argList.addAll(COMMANDS);
			return argList.stream().filter(a -> a.startsWith(args[0])).collect(Collectors.toList());
		}
		if(args.length == 2) {
			switch(args[0]) {
				case "tpbiome":
					argList.addAll(BIOMES);
					return argList.stream().filter(a -> a.startsWith(args[1].toUpperCase())).collect(Collectors.toList());
				default:
			}
		}
		return Collections.emptyList();
	}
}