package de.elwiron.bedwars.listener;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;


public class WeatherChange implements Listener {

	public static Plugin plugin;

	public WeatherChange(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void Place(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	

}
