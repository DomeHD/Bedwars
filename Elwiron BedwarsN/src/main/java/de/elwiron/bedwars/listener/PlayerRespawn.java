package de.elwiron.bedwars.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;



public class PlayerRespawn implements Listener{

	public static Plugin plugin;
	
	public PlayerRespawn(BedWars plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerR(PlayerRespawnEvent e){
		if(BedWars.getInstance().getState() == GameState.LOBBY || BedWars.getInstance().getState() == GameState.MAINTENANCE){
			e.setRespawnLocation(Join.spawn);
		}else if(BedWars.getInstance().getState() == GameState.RESTARTING) {
			e.setRespawnLocation(Join.spawn);
		}else if(BedWars.getInstance().getState() == GameState.PLAYING){
				Player p = e.getPlayer();
				e.setRespawnLocation(Teams.getTeamByPlayer(p).getSpawn());
		}
		
	}
	
}
