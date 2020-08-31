package org.polydev.gaea.structures.loot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.polydev.gaea.structures.loot.functions.AmountFunction;
import org.polydev.gaea.structures.loot.functions.DamageFunction;
import org.polydev.gaea.structures.loot.functions.EnchantWithLevelsFunction;
import org.polydev.gaea.structures.loot.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a single item entry within a Loot Table pool.
 */
public class Entry {
    private final JSONObject entry;
    private final Material item;
    private final long weight;
    private final List<Function> functions = new ArrayList<>();

    /**
     * Instantiates an Entry from a JSON representation.
     * @param entry The JSON Object to instantiate from.
     */
    public Entry(JSONObject entry) {
        this.entry = entry;
        this.item = Material.valueOf(entry.get("name").toString().toUpperCase());
        this.weight = (long) entry.get("weight");
        if(this.entry.containsKey("functions")) {
            for (Object function : (JSONArray) this.entry.get("functions")) {
                switch(((String) ((JSONObject) function).get("function"))) {
                    case "set_count":
                        long max = (long) ((JSONObject) ((JSONObject) function).get("count")).get("max");
                        long min = (long) ((JSONObject) ((JSONObject) function).get("count")).get("min");
                        functions.add(new AmountFunction(Math.toIntExact(min), Math.toIntExact(max)));
                        break;
                    case "set_damage":
                        long maxDamage = (long) ((JSONObject) ((JSONObject) function).get("damage")).get("max");
                        long minDamage = (long) ((JSONObject) ((JSONObject) function).get("damage")).get("min");
                        functions.add(new DamageFunction(Math.toIntExact(minDamage), Math.toIntExact(maxDamage)));
                        break;
                    case "enchant_with_levels":
                        long maxEnchant = (long) ((JSONObject) ((JSONObject) function).get("levels")).get("max");
                        long minEnchant = (long) ((JSONObject) ((JSONObject) function).get("levels")).get("min");
                        JSONArray disabled = null;
                        if(((JSONObject) function).containsKey("disabled_enchants")) disabled = (JSONArray) ((JSONObject) function).get("disabled_enchants");
                        functions.add(new EnchantWithLevelsFunction(Math.toIntExact(minEnchant), Math.toIntExact(maxEnchant), disabled));
                        break;
                }
            }
        }
    }

    /**
     * Fetches a single ItemStack from the Entry, applying all functions to it.
     * @param r The Random instance to apply functions with
     * @return ItemStack - The ItemStack with all functions applied.
     */
    public ItemStack getItem(Random r) {
        ItemStack item = new ItemStack(this.item, 1);
        for(Function f : functions) {
            item = f.apply(item, r);
        }
        return item;
    }

    /**
     * Gets the weight attribute of the Entry.
     * @return long - The weight of the Entry.
     */
    public long getWeight() {
        return this.weight;
    }
}
