package de.elwiron.bedwars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;

public class ServerListPing implements Listener{

    public static BedWars plugin;


    public ServerListPing(BedWars plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void ListPing(ServerListPingEvent e){

        if(BedWars.getInstance().getState() == GameState.MAINTENANCE){
            e.setMotd("§cWartungs Arbeiten!");
            e.setMaxPlayers(10);
        }else if(BedWars.getInstance().getState() == GameState.LOBBY){
            e.setMotd("§aLobby Phase");
            e.setMaxPlayers(BedWars.getMaxPlayers());
        }else {
            e.setMotd("§eim Spiel");
            e.setMaxPlayers(100);
        }

    }
}
