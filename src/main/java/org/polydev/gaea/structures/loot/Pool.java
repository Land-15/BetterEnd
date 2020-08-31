package org.polydev.gaea.structures.loot;

import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.polydev.gaea.math.ProbabilityCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a Loot Table pool, or a set of items to be fetched independently.
 */
public class Pool {
    private final JSONObject pool;
    private final ProbabilityCollection<Entry> entries = new ProbabilityCollection<>();

    /**
     * Instantiates a Pool from a JSON representation.
     * @param pool The JSON Object to instantiate from.
     */
    public Pool(JSONObject pool) {
        this.pool = pool;
        for(Object entryJSON : (JSONArray) pool.get("entries")) {
            Entry entry = new Entry((JSONObject) entryJSON);
            entries.add(entry, Math.toIntExact(entry.getWeight()));
        }
    }

    /**
     * Fetches a list of items from the pool using the provided Random instance.
     * @param r The Random instance to use.
     * @return List&lt;ItemStack&gt; - The list of items fetched.
     */
    public List<ItemStack> getItems(Random r) {
        int max = Math.toIntExact((long) ((JSONObject) pool.get("rolls")).get("max"));
        int min = Math.toIntExact((long) ((JSONObject) pool.get("rolls")).get("min"));
        int rolls = r.nextInt(max-min+1)+min;
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < rolls; i++) {
            items.add(entries.get(r).getItem(r));
        }
        return items;
    }
}
