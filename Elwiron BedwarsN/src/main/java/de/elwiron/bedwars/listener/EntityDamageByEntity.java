package de.elwiron.bedwars.listener;


import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.spigot.events.PlayerJoin;

public class EntityDamageByEntity implements Listener {


    public static Plugin plugin;

    public EntityDamageByEntity(BedWars plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void DamageByEntity(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;

        if(BedWars.getInstance().getState() == GameState.PLAYING){
            OfflinePlayer op1 = (OfflinePlayer) e.getDamager();
            OfflinePlayer op2 = (OfflinePlayer) e.getEntity();

            if(Teams.isSpectator(op1.getPlayer()) || Teams.isSpectator(op2.getPlayer())){
                e.setCancelled(true);
            }

            if(PlayerJoin.sb.getPlayerTeam(op1).equals(PlayerJoin.sb.getPlayerTeam(op2))) {
                e.setCancelled(true);
            }

        }

    }

}
