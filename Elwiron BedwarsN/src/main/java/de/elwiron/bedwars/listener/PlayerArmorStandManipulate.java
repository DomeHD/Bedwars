package de.elwiron.bedwars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;

public class PlayerArmorStandManipulate implements Listener {

	public static Plugin plugin;

	public PlayerArmorStandManipulate(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerM(PlayerArmorStandManipulateEvent e) {
		if(BedWars.getInstance().getState() != GameState.PLAYING){
			e.setCancelled(true);
		}
	}
}
