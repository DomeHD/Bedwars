package de.elwiron.bedwars.gamemodes;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.listener.InventoryClick;
import de.elwiron.bedwars.manager.ShopSpawnManager;
import de.elwiron.bedwars.manager.SpawnerManager;
import de.elwiron.bedwars.manager.scoreboard.ScoreboardManagerBedwars;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.spigot.events.PlayerJoin;

import java.util.HashMap;
import java.util.UUID;

public class Gamestart {

	public static final Gamestart OBJ = new Gamestart();
	
    @SuppressWarnings("deprecation")
    public void gamestart() {
        for(Teams t : Teams.values()){
            if (!InventoryClick.getTeam().containsKey(t.getDisplayName())) {
            	InventoryClick.getTeam().put(t.getDisplayName(), new HashMap<UUID, Player>());
            }            
        }
        
        //give all Players a Team and sendig to game Map!
        for(Team te : PlayerJoin.sb.getTeams()){
        	for(Player all : Bukkit.getOnlinePlayers()){
        		if(te.hasPlayer(all)) te.removePlayer(all);
        	} 	
        }
        for(Teams t : Teams.values()){
            if(t == Teams.SPECTATOR || t == Teams.NOTEAM){
                continue;
            }
            //if(InventoryClick.getTeam().get(t.getDisplayName()).isEmpty()) continue;
            for (Player all : InventoryClick.getTeam().get(t.getDisplayName()).values()) {
                PlayerJoin.sb.getTeam(t.getDisplayName()).addPlayer(all);
                all.teleport(t.getSpawn());
                t.getPlayers().add(all);
            }
        }
        // give a NoTeam a Team
       for (Player all :  InventoryClick.getNoTeam()) {
            Teams teamNoPlayer = null;
            int i = 1;
            for(Teams t : BedWars.getInstance().getAktivTeams()) {
                if (t == Teams.SPECTATOR || t == Teams.NOTEAM) {
                    continue;
                }
                if(i > BedWars.getInstance().getMaxTeams()){
                    break;
                }

                if(teamNoPlayer == null){
                    teamNoPlayer = t;
                }else {
                    if(teamNoPlayer.getPlayers().size() > t.getPlayers().size()){
                        teamNoPlayer = t;
                        continue;
                    }
                }
                
            }
            PlayerJoin.sb.getTeam(teamNoPlayer.getDisplayName()).addPlayer(all);
            all.teleport(teamNoPlayer.getSpawn());
            teamNoPlayer.getPlayers().add(all);
            i++;
        }

        for(Teams t : Teams.values()){
            if(t.getPlayers().isEmpty()){
                t.setLife(false);
                t.setRespawn(false);
            }
        }
        for(OfflinePlayer all : PlayerJoin.sb.getTeam(Teams.NOTEAM.getName()).getPlayers()){
        	 PlayerJoin.sb.getTeam(Teams.NOTEAM.getName()).removeEntry(all.getName());
        }
        
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getInventory().clear();
            all.setHealth(20);
            all.setFoodLevel(20);
            all.sendMessage(BedWars.getInstance().getPrefix() + "§eDas Spiel beginnt");
            
            all.setGameMode(GameMode.SURVIVAL);
            all.updateInventory();
            
            all.sendMessage(BedWars.getInstance().getPrefix() + "Du spielst in Team " + PlayerJoin.sb.getPlayerTeam(all).getDisplayName());
            new ScoreboardManagerBedwars(all);
        }
        SpawnerManager.spawnItem(LobbyTime.OBJ.getPlayMap());
        new ShopSpawnManager().spawnShops(LobbyTime.OBJ.getPlayMap());   
    }
}
