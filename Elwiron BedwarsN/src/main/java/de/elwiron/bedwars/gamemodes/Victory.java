package de.elwiron.bedwars.gamemodes;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;
import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.listener.Join;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.main.Main;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.api.StatsAPI;
import de.elwiron.mangemant.spigot.nick.Nick;

public class Victory {

	public static final Victory OBJ = new Victory();
	
	@SuppressWarnings("deprecation")
	public void victoryBuild(Teams team, String NoName) {
		BedWars.getInstance().setState(de.elwiron.bedwars.utils.GameState.RESTARTING);
		CloudServer.getInstance().setServerState(ServerState.OFFLINE);
		
		if(team == null){
			for (Player all : Bukkit.getOnlinePlayers()){
				if(Nick.isNicked(all)){
					Nick.unnick(all);
					all.sendMessage(Main.getInstance().getPrefix() + "§5Du wurdes endnickt!");
				}
		        all.getInventory().setHelmet(null);
		        all.getInventory().setChestplate(null);
		        all.getInventory().setLeggings(null);
		        all.getInventory().setBoots(null);
		        all.getInventory().clear();
				
		        all.getInventory().setItem(4, CreateIteam(Material.NETHER_STAR, "§bStats"));
		        all.getInventory().setItem(8, CreateIteam(Material.SLIME_BALL, "§cQuit"));
		        
				PlayerAPI.getPlayerTypes().get(all.getUniqueId()).setTabprefix();
				all.teleport(Join.spawn);
				all.sendTitle("&5Keiner","§ahat gewonnen!");
				all.setGameMode(GameMode.ADVENTURE);
				
				if(Teams.getTeamByPlayer(all).getName().equals(team.getName())){
					StatsAPI.getPlayerStats().get(all.getUniqueId()).addWins(1);
				}else {
					StatsAPI.getPlayerStats().get(all.getUniqueId()).addLose(1);
				}
				
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {

				public void run() {
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.kickPlayer(BedWars.getInstance().getPrefix() + "Du bist wieder auf der Lobby");
					}
					Bukkit.getServer().shutdown();
				}
			}, 10 * 20);

		}


		for (Player all : Bukkit.getOnlinePlayers()){
			if(NoName != null) {
				if(all.getName().equals(NoName)) {
					continue;
				}
			}

			if(Nick.isNicked(all)){
				Nick.unnick(all);
				all.sendMessage(Main.getInstance().getPrefix() + "§5Du wurdes endnickt!");
			}
			
	        all.getInventory().setHelmet(null);
	        all.getInventory().setChestplate(null);
	        all.getInventory().setLeggings(null);
	        all.getInventory().setBoots(null);
	        all.getInventory().clear();
			
	        all.getInventory().setItem(4, CreateIteam(Material.NETHER_STAR, "§bStats"));
	        all.getInventory().setItem(8, CreateIteam(Material.SLIME_BALL, "§cQuit"));
	        
			PlayerAPI.getPlayerTypes().get(all.getUniqueId()).setTabprefix();
			all.teleport(Join.spawn);
			all.sendTitle(team.getDisplayName(),"§ahat gewonnen!");
			all.setGameMode(GameMode.ADVENTURE);
		
		
			if(Teams.getTeamByPlayer(all).getName().equals(team.getName())){
				StatsAPI.getPlayerStats().get(all.getUniqueId()).addWins(1);
				PlayerAPI.getPlayerTypes().get(all.getUniqueId()).getCoins().addCoins(30);
			}else {
				StatsAPI.getPlayerStats().get(all.getUniqueId()).addLose(1);
			}
		}


		Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {

			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.kickPlayer(BedWars.getInstance().getPrefix() + "Du bist wieder auf der Lobby");
				}
				
				
                CloudServer.getInstance().setServerState(ServerState.OFFLINE); 
                CloudServer.getInstance().update();
				Bukkit.getServer().shutdown();
			}
		}, 10 * 20);

	}

	private ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		item.setItemMeta(meta);

		return item;
	}
}