package de.elwiron.bedwars.listener;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;

import java.util.ArrayList;


public class BlockPlace implements Listener {

	public static Plugin plugin;
	private static ArrayList<Location> blocklist = new ArrayList<>();

	public BlockPlace(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void Place(BlockPlaceEvent e) {
		if (BedWars.getInstance().getState() == GameState.LOBBY || BedWars.getInstance().getState() == GameState.RESTARTING) {
			e.setCancelled(true);
		}else if(BedWars.getInstance().getState() == GameState.PLAYING){
			if(Teams.isSpectator(e.getPlayer())){
				e.setCancelled(true);
				return;
			}

			if(e.getBlock().getType() == Material.BED_BLOCK){
				e.setCancelled(true);
				return;
			}
			blocklist.add(e.getBlock().getLocation());
		}

		
	}
	public static ArrayList<Location> getBlocklist() {
		return blocklist;
	}
}
