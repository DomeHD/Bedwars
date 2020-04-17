package de.elwiron.bedwars.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.gamemodes.LobbyTime;
import de.elwiron.bedwars.gamemodes.Victory;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;


public class PlayerQuit implements Listener {

	public static Plugin plugin;

	public PlayerQuit(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerQ(PlayerQuitEvent e) {
		Player p = e.getPlayer();


		if (BedWars.getInstance().getState() == GameState.LOBBY) {
			e.setQuitMessage("§c<< §7" + p.getName());
			InventoryClick.quitAllTeams(p, false);
			
			if(BedWars.getMaxPlayers() == 2) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.kickPlayer(BedWars.getInstance().getPrefix() + "Die Runde wurde benndet, da einer vorzeitig das Spiel Verlassen hat");
				}
				Bukkit.getServer().shutdown();
				return;
			}
			
			if (Bukkit.getOnlinePlayers().size() <= ConfigCreator.cfg.getInt("BedWars.MinPlayers")) {
				LobbyTime.OBJ.setTimeGo(false);
				LobbyTime.OBJ.setContdown(ConfigCreator.cfg.getInt("BedWars.Lobby.Time"));
				Bukkit.getScheduler().cancelTask(LobbyTime.OBJ.getS());
				for(Player all : Bukkit.getOnlinePlayers()){
					all.setLevel(LobbyTime.OBJ.getContdown());
				}
			}
		}else {
			if(Bukkit.getOnlinePlayers().size() == 0) {
				Bukkit.getServer().shutdown();
			}
			    e.setQuitMessage(null);
				Teams ta = Teams.getTeamByPlayer(p);
				ta.getPlayers().remove(p);

				if(ta.getPlayers().isEmpty()){
					ta.setLife(false);
				}

				Teams t = PlayerDeath.checkWin();
				if(t != null){
					Victory.OBJ.victoryBuild(t, p.getName());
				}



		}
	}
}
