package de.elwiron.bedwars.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;

public class CraftItem implements Listener {

	public static Plugin plugin;
	
	public CraftItem(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void CraftI(CraftItemEvent e) {
		if(e.getCurrentItem().getType() == Material.IRON_BLOCK || e.getCurrentItem().getType() == Material.IRON_INGOT) {
			e.setCancelled(true);
		}
	}

}
