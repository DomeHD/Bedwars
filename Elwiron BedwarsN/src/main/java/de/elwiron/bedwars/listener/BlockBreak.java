package de.elwiron.bedwars.listener;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.manager.scoreboard.ScoreboardManagerBedwars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.events.PlayerJoin;


public class BlockBreak implements Listener {

	public static Plugin plugin;
	
	public BlockBreak(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void Break(BlockBreakEvent e) {
		if (BedWars.getInstance().getState() == GameState.LOBBY || BedWars.getInstance().getState() == GameState.RESTARTING) {
			e.setCancelled(true);
		}

		if(BedWars.getInstance().getState() == GameState.PLAYING){
			if(Teams.isSpectator(e.getPlayer())){
				e.setCancelled(true);
				return;
			}
			
			//beds
			if(e.getBlock().getType() == Material.BED_BLOCK){

				for(Teams t : Teams.values()){
					if(t.getBed() == null){
						continue;
					}
					if(t.getBed().equals(e.getBlock().getLocation()) ||
							t.getBed().clone().add(-1, 0, 0).equals(e.getBlock().getLocation()) ||
							t.getBed().clone().add(1, 0, 0).equals(e.getBlock().getLocation()) ||
					 		t.getBed().clone().add(0, 0, -1).equals(e.getBlock().getLocation()) ||
							t.getBed().clone().add(0, 0, 1).equals(e.getBlock().getLocation())){

						if(t == Teams.getTeamByDisplayName(PlayerJoin.sb.getPlayerTeam(e.getPlayer()).getName())){
							break;
						}


						for(Player all : t.getPlayers()){
							all.sendTitle(t.getPrefix() + "Bett Zerstört", t.getPrefix() + "von Team " + t.getDisplayName());
							all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
						}

						
						for(Player all : Bukkit.getOnlinePlayers()){
							new ScoreboardManagerBedwars(all).updateScoreboard(all);
							all.sendMessage(BedWars.getInstance().getPrefix() + "Das Bett von Team " + t.getDisplayName() + " §7wurde von " + PlayerJoin.sb.getPlayerTeam(e.getPlayer()).getPrefix() + e.getPlayer().getDisplayName() + " §7Zerstört" );					
						}
						
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						if(e.getBlock().getLocation().clone().add(-1, 0, 0).getBlock().getType() == Material.BED_BLOCK) e.getBlock().getLocation().clone().add(-1, 0, 0).getBlock().setType(Material.AIR);
						if(e.getBlock().getLocation().clone().add(1, 0, 0).getBlock().getType() == Material.BED_BLOCK) e.getBlock().getLocation().clone().add(1, 0, 0).getBlock().setType(Material.AIR);
						if(e.getBlock().getLocation().clone().add(0, 0, -1).getBlock().getType() == Material.BED_BLOCK) e.getBlock().getLocation().clone().add(0, 0, -1).getBlock().setType(Material.AIR);
						if(e.getBlock().getLocation().clone().add(0, 0, 1).getBlock().getType() == Material.BED_BLOCK) e.getBlock().getLocation().clone().add(0, 0, 1).getBlock().setType(Material.AIR);
						t.setRespawn(false);
						PlayerAPI.getPlayerTypes().get(e.getPlayer().getUniqueId()).getCoins().addCoins(15);
						e.getPlayer().sendMessage(BedWars.getInstance().getPrefix() + "Du hast §e15 §7Coins Für das Zerstören Bed von Team " + t.getPrefix() + t.getName() + " §7Bekommen!");
					}
				}
			}


			//all blocks
			if(BlockPlace.getBlocklist().contains(e.getBlock().getLocation())){
				e.setCancelled(false);
				BlockPlace.getBlocklist().remove(e.getBlock().getLocation());
			}else {
				e.setCancelled(true);
			}

		}

	}

	
}
