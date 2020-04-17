package de.elwiron.bedwars.listener;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.gamemodes.LobbyTime;
import de.elwiron.bedwars.manager.scoreboard.ScoreboardManagerBedwars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.events.PlayerJoin;
import de.elwiron.mangemant.utils.Rang;


public class Join implements Listener {

	public static Plugin plugin;
	public static Location spawn = null;

	static {
		Location configSpawn = (Location) ConfigCreator.location.get("BedWars.Spawn.Loction");
		if (!configSpawn.equals(new Location(Bukkit.getWorld("world"), 0, 0,0))) {
			spawn = (Location) ConfigCreator.location.get("BedWars.Spawn.Loction");
		}
	} 

	public Join(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerJ(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
	
		
		if (BedWars.getInstance().getState() == GameState.LOBBY) {
			if(!PlayerAPI.getPlayerTypes().containsKey(p.getUniqueId())){
				PlayerAPI.getPlayerTypes().put(p.getUniqueId(), new PlayerAPI(p));  
				PlayerAPI.getPlayerTypes().get(p.getUniqueId()).setRang();
			}
			setLobbyInventory(p);
			if(BedWars.getInstance().getGameOwner() != null) {
				if(BedWars.getInstance().getGameOwner().equals(p.getUniqueId())){
					p.sendMessage(BedWars.getInstance().getPrefix() + "§7Du hast eine private runde gestartet, deswegegen kannst du folgendes Benutzen:");
					p.sendMessage(BedWars.getInstance().getPrefix() + "§6/Start");
					p.sendMessage(BedWars.getInstance().getPrefix() + "§6Forcemap, über das icon.");
				}

			}
		if (spawn != null) {
				p.teleport(spawn);
			} else {
				if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN)) {
					p.sendMessage(BedWars.getInstance().getPrefix() + "§cDu must den spawn punkt noch setzten!");
					BedWars.getInstance().setState(GameState.MAINTENANCE);
				} else {
					p.kickPlayer("BedWars nicht spielbar!");
				}
	
			}
		p.setScoreboard(PlayerJoin.sb);
		InventoryClick.getNoTeam().add(p);
		LobbyTime.OBJ.lobby(p);
		}else {
			e.setJoinMessage(null);
			p.setScoreboard(PlayerJoin.sb);
			new ScoreboardManagerBedwars(p);
			PlayerDeath.setSpec(p, true);
		}
		

	}

	private void setLobbyInventory(Player p) {
		// --- Inventory Clear ---//
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.updateInventory();
		for(PotionEffect pe : p.getActivePotionEffects()){
			p.removePotionEffect(pe.getType());
		}

		// --- Inventory Setting ---//

		p.getInventory().setItem(0, CreateIteam(Material.CHEST, "§cTeams"));
		p.getInventory().setItem(2, CreateIteam(Material.NETHER_STAR, "§bStats"));

		p.getInventory().setItem(8, CreateIteam(Material.SLIME_BALL, "§cQuit"));
		
		//BedWars.getInstance().getGameOwner().equals(p.getUniqueId())
		if(BedWars.getInstance().getGameOwner() != null) {
			if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.YOUTUBER) || BedWars.getInstance().getGameOwner().equals(p.getUniqueId())) {
				p.getInventory().setItem(4, CreateIteam(Material.BOOK, "§5Force Map"));
			}
			if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.YOUTUBER)) {
				p.getInventory().setItem(4, CreateIteam(Material.BOOK, "§5Force Map"));
			}
		}else {
			if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.YOUTUBER)) {
				p.getInventory().setItem(4, CreateIteam(Material.BOOK, "§5Force Map"));
			}
		}




		// --- Gamemode Setting ---//

		p.setHealth(20);
		p.setGameMode(GameMode.ADVENTURE);
	}

	private ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		item.setItemMeta(meta);

		return item;
	}
	
}
