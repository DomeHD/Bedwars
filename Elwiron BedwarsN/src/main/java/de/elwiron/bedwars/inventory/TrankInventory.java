package de.elwiron.bedwars.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.utils.inventory.BowLayer;
import de.elwiron.bedwars.utils.inventory.TrankLayer;

public class TrankInventory {

    public void openInventory(Player p){

        Inventory inv = p.getServer().createInventory(null, 9 * 3, "§6Shop - Trank");

        for(int i = 0; i < 9*3; i++){
            inv.setItem(i, createItem(160, 7, "§0"));
        }

        inv.setItem(10, TrankLayer.SPRUNG.getItemStack());
        inv.setItem(11, TrankLayer.SPEED.getItemStack());
        inv.setItem(12, TrankLayer.UNSICHTBAR.getItemStack());

        inv.setItem(14, TrankLayer.FIRE.getItemStack());
        inv.setItem(15, TrankLayer.REGENARTION.getItemStack());
        inv.setItem(16, TrankLayer.STARKE.getItemStack());

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
