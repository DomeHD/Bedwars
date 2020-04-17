package de.elwiron.bedwars.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.utils.inventory.PickaxtLayer;

public class PickAxtInventroy {

    public void openInventory(Player p){

        Inventory inv = p.getServer().createInventory(null, 9 * 3, "§6Shop - Spitzhacke");

        for(int i = 0; i < 9*3; i++){
            inv.setItem(i, createItem(160, 7, "§0"));
        }

        inv.setItem(10, PickaxtLayer.WOODPICKAXT.getItemStack());
        inv.setItem(13, PickaxtLayer.STONEPICKAXT.getItemStack());
        inv.setItem(16, PickaxtLayer.IRONPICKAXT.getItemStack());

        inv.setItem(18, createItem(288, 0, "§bZurück"));

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
