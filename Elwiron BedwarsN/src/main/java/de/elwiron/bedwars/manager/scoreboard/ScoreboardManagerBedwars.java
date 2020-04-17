package de.elwiron.bedwars.manager.scoreboard;

import org.bukkit.entity.Player;


import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.gamemodes.LobbyTime;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.musterbukkit.replaysystem.main.ReplayAPI;

public class ScoreboardManagerBedwars {

	public PacketScoreboard scoreboard;
	
	public ScoreboardManagerBedwars(Player p){
		this.scoreboard = new PacketScoreboard(p);
		
		scoreboard.remove();
		scoreboard.sendSidebar("§n§bBedwars");
		if(BedWars.getInstance().getState() != GameState.PLAYING) return;
		scoreboard.setLine(16, "§1");
		scoreboard.setLine(15, "§7Map:");
		scoreboard.setLine(14, "§e" + LobbyTime.OBJ.getPlayMap());
		scoreboard.setLine(13, "§2");
		scoreboard.setLine(12, "§7Replay-ID");
		scoreboard.setLine(11, "§e" + ReplayAPI.getReplayID());
		scoreboard.setLine(10, "§3");

		int i = 1;
		for(Teams t : BedWars.getInstance().getAktivTeams()){
			if(t.isRespawn()) {
				scoreboard.setLine(i, "§a√ " + t.getDisplayName() + ": §b" + t.getPlayers().size());
			}else {
				scoreboard.setLine(i, "§cχ " +t.getDisplayName() + ": §b" + t.getPlayers().size());
			}
			i++;
		}
		
		
		
		
	}
	
	public void updateScoreboard(Player p){
	
		
		int i = 1;
		for(Teams t : BedWars.getInstance().getAktivTeams()){
			scoreboard.removeLine(i);
			i++;
		}
		scoreboard.removeLine(14);
		scoreboard.removeLine(11);
		
		
		scoreboard.sendSidebar("§n§bBedwars");
		if(BedWars.getInstance().getState() != GameState.PLAYING) return;
		scoreboard.setLine(16, "§1");
		scoreboard.setLine(15, "§7Map:");
		scoreboard.setLine(14, "§e" + LobbyTime.OBJ.getPlayMap());
		scoreboard.setLine(13, "§2");
		scoreboard.setLine(12, "§7Replay-ID");
		scoreboard.setLine(11, "§e" + ReplayAPI.getReplayID());
		scoreboard.setLine(10, "§3");

	    i = 1;
		for(Teams t : BedWars.getInstance().getAktivTeams()){
			if(t.isRespawn()) {
				scoreboard.setLine(i, "§a√ " + t.getDisplayName() + ": §b" + t.getPlayers().size());
			}else {
				scoreboard.setLine(i, "§cχ " +t.getDisplayName() + ": §b" + t.getPlayers().size());
			}
			
			i++;
		}
		
	}
	
}
