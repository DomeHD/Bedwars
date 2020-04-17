package de.elwiron.bedwars.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;



public class EntityDamage implements Listener {

	public static Plugin plugin;

	public EntityDamage(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void Damage(EntityDamageEvent e) {
		if(BedWars.getInstance().getState() == GameState.LOBBY){
			if (e.getEntity() instanceof Player) {
				e.setCancelled(true);
			}
		}else if(BedWars.getInstance().getState() == GameState.PLAYING){
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();

				if(Teams.isSpectator(p)) e.setCancelled(true);
			}
		}
	}


}
