package org.polydev.gaea.structures;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class StructureUtil {
    public static List<Location> getChestsIn(Location minLoc, Location maxLoc) {
        List<Location> locations = new ArrayList<>();
        for(Location location: getLocationListBetween(minLoc, maxLoc)) {
            BlockState blockState = location.getBlock().getState();
            if(blockState instanceof Container) {
                if(blockState instanceof Chest) {
                    InventoryHolder holder = ((Chest) blockState).getInventory().getHolder();
                    if(holder instanceof DoubleChest) {
                        DoubleChest doubleChest = ((DoubleChest) holder);
                        Location leftSideLocation = ((Chest) doubleChest.getLeftSide()).getLocation();
                        Location rightSideLocation = ((Chest) doubleChest.getRightSide()).getLocation();

                        Location roundedLocation = new Location(location.getWorld(), Math.floor(location.getX()), Math.floor(location.getY()), Math.floor(location.getZ()));

                        // Check to see if this (or the other) side of the chest is already
                        // in the list
                        if((leftSideLocation.distance(roundedLocation) < 1 && isNotAlreadyIn(locations, rightSideLocation))
                                || (rightSideLocation.distance(roundedLocation) < 1 && isNotAlreadyIn(locations, leftSideLocation))) {
                            locations.add(roundedLocation);

                        }

                    } else if(holder instanceof Chest) {
                        locations.add(location);
                    }
                } else {
                    locations.add(location);
                }
            }
        }
        return locations;
    }

    public static List<Location> getLocationListBetween(Location loc1, Location loc2) {
        int lowX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int lowY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int lowZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        List<Location> locs = new ArrayList<>();
        for(int x = 0; x <= Math.abs(loc1.getBlockX() - loc2.getBlockX()); x++) {
            for(int y = 0; y <= Math.abs(loc1.getBlockY() - loc2.getBlockY()); y++) {
                for(int z = 0; z <= Math.abs(loc1.getBlockZ() - loc2.getBlockZ()); z++) {
                    locs.add(new Location(loc1.getWorld(), (double) lowX + x, (double) lowY + y, (double) lowZ + z));
                }
            }
        }
        return locs;
    }

    private static boolean isNotAlreadyIn(List<Location> locations, Location location) {
        for(Location auxLocation: locations) {
            if(location.distance(auxLocation) < 1) {
                return false;
            }
        }
        return true;
    }
}
