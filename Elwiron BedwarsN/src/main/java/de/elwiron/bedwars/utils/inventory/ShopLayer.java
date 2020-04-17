package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ShopLayer {


    ARMOR("Rüstung", "§6Rüstung",CreateIteam(Material.LEATHER_CHESTPLATE, "§6Rüstung")),
    WEAPONS("Waffen", "§6Waffen",CreateIteam(Material.IRON_SWORD, "§6Waffen")),
    BOW("Bogen", "§6Bogen",CreateIteam(Material.BOW, "§6Bogen")),
    PICKAXT("Spitzhacke", "§6Spitzhacke",CreateIteam(Material.IRON_PICKAXE, "§6Spitzhacke")),
    EAT("Essen", "§6Essen",CreateIteam(Material.COOKED_BEEF, "§6Essen")),
    BLOCKS("Blöcke", "§6Blöcke",CreateIteam(Material.STAINED_CLAY, "§6Blöcke")),
    SPECIAL("Spezial Blöcke", "§6Spezial Blöcke",CreateIteam(Material.BLAZE_ROD, "§6Spezial Blöcke")),
	TRANK("Tränke", "§6Tränke",CreateIteam(Material.POTION, "§6Tränke"));

    private String name;
    private String displayName;
    private ItemStack itemStack;

    ShopLayer(String name, String displayName,ItemStack itemStack){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
    }

    private static ItemStack CreateIteam(Material m, String Name) {

        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        item.setItemMeta(meta);

        return item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public String getDisplayName() {
        return displayName;
    }
}
