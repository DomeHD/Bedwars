package de.elwiron.bedwars.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;

public class BlockExplode implements Listener{

	public static Plugin plugin;
	
	public BlockExplode(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void Explode(EntityExplodeEvent e) {
		if(BedWars.getInstance().getState() == GameState.PLAYING) {
			e.setCancelled(true);
		}
	}
}
