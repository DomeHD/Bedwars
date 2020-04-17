package de.elwiron.bedwars.inventory;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.inventory.BlockLayer;

import java.util.ArrayList;

public class BlockInventroy {

    public void openInventory(Player p){

        Inventory inv = p.getServer().createInventory(null, 9 * 3, "§6Shop - Blöcke");

        for(int i = 0; i < 9*3; i++){
            inv.setItem(i, createItem(160, 7, "§0"));
        }

        inv.setItem(10, setColorBlocks(BlockLayer.BLOCK.getItemStack().getType(), BlockLayer.BLOCK.getDisplayName(),BlockLayer.BLOCK.getLore(), Teams.getTeamByPlayer(p).getColor(), BlockLayer.BLOCK.getStack()));
        inv.setItem(11, setColorBlocks(BlockLayer.WOOL.getItemStack().getType(), BlockLayer.WOOL.getDisplayName(), BlockLayer.WOOL.getLore(), Teams.getTeamByPlayer(p).getColor(),BlockLayer.WOOL.getStack()));

        inv.setItem(14, BlockLayer.ENDERBLOCK.getItemStack());
        
        inv.setItem(16, BlockLayer.EISENBLOCK.getItemStack());

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


    public ItemStack setColorBlocks(Material m, String name, String l,DyeColor color,int size){

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack i = new ItemStack(m, 1, color.getWoolData());
        i.setAmount(size);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public ItemStack setColorBlockGlas(Material m, String name, String l,DyeColor color,int size){

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack i = new ItemStack(m, 1, color.getData());
        i.setAmount(size);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

}
