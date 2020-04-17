package de.elwiron.bedwars.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.inventory.SpezialLayer;


public class PlayerMove implements Listener {

	public static Plugin plugin;

	public PlayerMove(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerM(PlayerMoveEvent e) {
		if(BedWars.getInstance().getState()!= GameState.PLAYING){
			e.getPlayer().setFoodLevel(20);
		}else {
			if(Teams.isSpectator(e.getPlayer())){
				e.getPlayer().setHealth(20);
				e.getPlayer().setFoodLevel(20);
				return;
			}
			if(e.getFrom().getY() <= 10 && e.getFrom().getY() >= 8) {
				e.getPlayer().setHealth(0);
			}
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()){
				if(PlayerInteract.getHomeTimer().containsKey(e.getPlayer())){
					Player p = e.getPlayer();
					Bukkit.getScheduler().cancelTask(PlayerInteract.getHomeTimer().get(p).getScheduler());
					PlayerInteract.getHomeTimer().remove(p);
					p.sendMessage(BedWars.getInstance().getPrefix() + "§cDie action wurde abgebrochen!");
					p.getInventory().addItem(SpezialLayer.HOME.getItemStack());
				}
			}

		}
	}

}
