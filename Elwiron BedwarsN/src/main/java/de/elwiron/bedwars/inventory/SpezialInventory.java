package de.elwiron.bedwars.inventory;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.utils.inventory.SpezialLayer;

public class SpezialInventory {
	
    public void openInventory(Player p){

        Inventory inv = p.getServer().createInventory(null, 9 * 5, "§6Shop - Spezial");

        for(int i = 0; i < 9*5; i++){
            inv.setItem(i, createItem(160, 7, "§0"));
        }

        inv.setItem(10, SpezialLayer.LADDER.getItemStack());
        inv.setItem(11, SpezialLayer.ENDERPEARL.getItemStack());

        inv.setItem(13, SpezialLayer.BLAZEROD.getItemStack());
        inv.setItem(14, SpezialLayer.HOME.getItemStack());

        inv.setItem(16, SpezialLayer.CHEST.getItemStack());
        
        inv.setItem(28, SpezialLayer.ANGEL.getItemStack());
        inv.setItem(29, SpezialLayer.FEUERZEUG.getItemStack());

        inv.setItem(31, SpezialLayer.WEBEN.getItemStack());
        inv.setItem(32, SpezialLayer.TNT.getItemStack());

        inv.setItem(34, SpezialLayer.ENDERCHEST.getItemStack());
        
        inv.setItem(36, createItem(288, 0, "§bZurück"));
        p.openInventory(inv);

    }


    private ItemStack createItem(int id, int subId, String name) {

        ItemStack i = new ItemStack(id, 1, (short) subId);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }
}
