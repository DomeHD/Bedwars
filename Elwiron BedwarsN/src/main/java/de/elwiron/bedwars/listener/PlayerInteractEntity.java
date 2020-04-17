package de.elwiron.bedwars.listener;


import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.inventory.ShopLayer;

public class PlayerInteractEntity implements Listener {


    public PlayerInteractEntity(BedWars plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void PlayerInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if(BedWars.getInstance().getState() == GameState.PLAYING) {
            if(Teams.isSpectator(p)){
                e.setCancelled(true);
                return;
            }
            if (e.getRightClicked() instanceof Villager) {
                e.setCancelled(true);
                createMainInv(p);
            }

        }

    }


    public static void createMainInv(Player p) {
        Inventory inv = p.getServer().createInventory(null, 9 * 5, "§6Shop");


        for (int i = 0; i < 9 * 5; i++) {
            inv.setItem(i, createItem(160, 7, "§0"));
        }

        inv.setItem(11, setColorBlocks(ShopLayer.BLOCKS.getItemStack().getType(), ShopLayer.BLOCKS.getDisplayName(), Teams.getTeamByPlayer(p).getColor()));
        inv.setItem(13, ShopLayer.PICKAXT.getItemStack());
        inv.setItem(15, ShopLayer.WEAPONS.getItemStack());
        //inv.setItem(16, ShopLayer.BOW.getItemStack());

        inv.setItem(28, ShopLayer.EAT.getItemStack());
        inv.setItem(30, ShopLayer.ARMOR.getItemStack());
        inv.setItem(32, ShopLayer.SPECIAL.getItemStack());
        inv.setItem(34, ShopLayer.TRANK.getItemStack());

        p.openInventory(inv);
    }

    public static ItemStack setColorBlocks(Material m,String name,DyeColor color){

        ItemStack i = new ItemStack(m, 1, color.getWoolData());
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }
    public static ItemStack setColorAmor(ItemStack itemStack,String name,DyeColor color){
        LeatherArmorMeta itemMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        itemMeta.setColor(color.getColor());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    private static ItemStack createItem(int id, int subId, String name) {

        ItemStack i = new ItemStack(id, 1, (short) subId);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }

}
