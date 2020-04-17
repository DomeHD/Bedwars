package de.elwiron.bedwars.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;


public class PlayerDropItem implements Listener {

	public static Plugin plugin;

	public PlayerDropItem(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerD(PlayerDropItemEvent e) {
		if(BedWars.getInstance().getState() != GameState.PLAYING){
			e.setCancelled(true);
		}else {
			if(Teams.isSpectator(e.getPlayer())){
				e.setCancelled(true);
			}
		}
			
	}
	@EventHandler
	public void PlayerP(PlayerPickupItemEvent e) {
		if(BedWars.getInstance().getState() != GameState.PLAYING){
			e.setCancelled(true);
		}else {
			if(Teams.isSpectator(e.getPlayer())){
				e.setCancelled(true);
			}
			if(e.getItem().getItemStack().getType() == Material.BED || e.getItem().getItemStack().getType() == Material.BED_BLOCK){
				e.setCancelled(true);
			}

			if(e.getItem().getItemStack().getType() == Material.STAINED_CLAY){
				ItemMeta im = e.getItem().getItemStack().getItemMeta(); // 
				im.setDisplayName("§6Clay");
				e.getItem().getItemStack().setItemMeta(im);
			}
			if(e.getItem().getItemStack().getType() == Material.ENDER_STONE){
				ItemMeta im = e.getItem().getItemStack().getItemMeta();
				im.setDisplayName("§6Enderstein");
				e.getItem().getItemStack().setItemMeta(im);
			}
			if(e.getItem().getItemStack().getType() == Material.IRON_BLOCK){
				ItemMeta im = e.getItem().getItemStack().getItemMeta();
				im.setDisplayName("§7Eisenblock");
				e.getItem().getItemStack().setItemMeta(im);
			}
			if(e.getItem().getItemStack().getType() == Material.WOOL){
				ItemMeta im = e.getItem().getItemStack().getItemMeta();
				im.setDisplayName("§6Wolle");
				e.getItem().getItemStack().setItemMeta(im);
			}
			if(e.getItem().getItemStack().getType() == Material.GLASS){
				ItemMeta im = e.getItem().getItemStack().getItemMeta();
				im.setDisplayName("§6Glas");
				e.getItem().getItemStack().setItemMeta(im);
			}
		}
	}

}
