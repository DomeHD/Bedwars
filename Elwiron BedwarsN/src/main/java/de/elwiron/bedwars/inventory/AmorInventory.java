package de.elwiron.bedwars.inventory;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.inventory.AmorLayer;

import java.util.ArrayList;

public class AmorInventory {

    public void openInventory(Player p){

        Inventory inv = p.getServer().createInventory(null, 9 * 3, "§6Shop - Rüstung");

        for(int i = 0; i < 9*3; i++){
        inv.setItem(i, createItem(160, 7, "§0"));
    }

        inv.setItem(10, setColorAmor(AmorLayer.LEATHERBOOTS.getItemStack(), AmorLayer.LEATHERBOOTS.getDisplayName(), Teams.getTeamByPlayer(p).getColor(), AmorLayer.LEATHERBOOTS.getLore()));
        inv.setItem(11, setColorAmor(AmorLayer.LEATHERLEGGINS.getItemStack(), AmorLayer.LEATHERLEGGINS.getDisplayName(), Teams.getTeamByPlayer(p).getColor(), AmorLayer.LEATHERLEGGINS.getLore()));
        inv.setItem(12, setColorAmor(AmorLayer.LEATHERCHESTPLATE.getItemStack(), AmorLayer.LEATHERCHESTPLATE.getDisplayName(), Teams.getTeamByPlayer(p).getColor(), AmorLayer.LEATHERCHESTPLATE.getLore()));
        inv.setItem(13, setColorAmor(AmorLayer.LEATHERHELM.getItemStack(), AmorLayer.LEATHERHELM.getDisplayName(), Teams.getTeamByPlayer(p).getColor(), AmorLayer.LEATHERHELM.getLore()));

        inv.setItem(14, AmorLayer.CHESTPLATE1.getItemStack());
        inv.setItem(15, AmorLayer.CHESTPLATE2.getItemStack());
        inv.setItem(16, AmorLayer.CHESTPLATE3.getItemStack());
        
        
        inv.setItem(18, createItem(288, 0, "§bZurück"));
        p.openInventory(inv);

}


    public ItemStack setColorAmor(ItemStack itemStack,String name,DyeColor color, String l){

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        LeatherArmorMeta itemMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        itemMeta.setColor(color.getColor());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
       
        return itemStack;
    }
    private ItemStack createItem(int id, int subId, String name) {

        ItemStack i = new ItemStack(id, 1, (short) subId);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }
}
