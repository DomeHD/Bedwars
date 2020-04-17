package de.elwiron.bedwars.listener;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.spigot.events.PlayerJoin;

public class PlayerChat implements Listener {


    public PlayerChat(BedWars plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void PlayerC(AsyncPlayerChatEvent e) {
        Player p  = e.getPlayer();

        if(BedWars.getInstance().getState() != GameState.PLAYING){
            e.setFormat(PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7>>" + e.getMessage());
        }else {
            if(Teams.isSpectator(p)){
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(Teams.isSpectator(all)){
                        all.sendMessage(PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7>>" + e.getMessage());
                    }
                }
                e.setCancelled(true);
                return;
            }

            Teams t = Teams.getTeamByPlayer(p);
            if(t.getPlayers().size() == 1){
                e.setFormat(PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7>>" + e.getMessage().replace("@a", ""));
            }else {
                if(e.getMessage().startsWith("@a")){
                    e.setFormat(PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7>>" + e.getMessage().replace("@a", ""));
                    return;
                }
                e.setCancelled(true);
                for(Player all : t.getPlayers()){
                    all.sendMessage(PlayerJoin.sb.getPlayerTeam(p).getPrefix() + "[TEAM]" + p.getName() + " §7>>" + e.getMessage().replace("@a", ""));
                }
            }
        }



    }
}
