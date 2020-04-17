package de.elwiron.bedwars.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class PlayerLogin implements Listener{

    public static BedWars plugin;


    public PlayerLogin(de.elwiron.bedwars.BedWars plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void Login(PlayerLoginEvent e){

        Player p = e.getPlayer();

       // Bukkit.getConsoleSender().sendMessage(p.getUniqueId() + "");
        
		if(!PlayerAPI.getPlayerTypes().containsKey(p.getUniqueId())){
			PlayerAPI.getPlayerTypes().put(p.getUniqueId(), new PlayerAPI(p));  
			PlayerAPI.getPlayerTypes().get(p.getUniqueId()).setRang();
		}
        
        if(BedWars.getInstance().getState() == GameState.PLAYING){
            e.allow();
        }else if(BedWars.getInstance().getState() == GameState.MAINTENANCE){
            if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN)) {
                e.allow();
                Bukkit.broadcastMessage(BedWars.getInstance().getPrefix() +"§e" + p.getName() + " §aist dem Server im Wartungmodus gejoint");
            }else {
                e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL, BedWars.getInstance().getPrefix() + "§cDer Server ist im Wartungmodus");
            }
            return;
        }

        
        if(BedWars.getInstance().getMaxPlayers() <= BedWars.getInstance().getServer().getOnlinePlayers().size()){
            if(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.PREMIUM)){
                for(Player all : Bukkit.getServer().getOnlinePlayers()){
                    if(!PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHiger(PlayerAPI.getPlayerTypes().get(all.getUniqueId()).getRang())){
                        all.kickPlayer(BedWars.getInstance().getPrefix() + "§cDu wurdes gekickt um einem VIP/Youtuber/Teammitgild Platz zu machen!");
                        e.allow();
                        break;
                    }
                }
            }else {
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, BedWars.getInstance().getPrefix() + "§6Die Runde ist voll.");
            }
        }
    }
}
