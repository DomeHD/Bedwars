package de.elwiron.bedwars.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DropItems  {

    BRONZE("Bronze", "§cBronze", Material.CLAY_BRICK, "§c"),
    IRON("Eisen", "§7Eisen", Material.IRON_INGOT,"§7"),
    GOLD("Gold", "§6Gold", Material.GOLD_INGOT, "§6");


    private String name;
    private String displayName;
    private Material material;
    private String prefix;


    DropItems(String name, String displayName, Material material, String prefix) {
        this.prefix = prefix;
        this.name = name;
        this.displayName = displayName;
        this.material = material;
    }

    public String getPrefix() {
        return prefix;
    }
    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

}
