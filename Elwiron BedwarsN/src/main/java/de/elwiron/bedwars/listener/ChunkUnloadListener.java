package de.elwiron.bedwars.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import de.elwiron.bedwars.BedWars;

public class ChunkUnloadListener implements Listener {
    public ChunkUnloadListener(BedWars plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        boolean villager = false;
        for (Entity entity : event.getChunk().getEntities()) {
            if(entity.getType() == EntityType.VILLAGER) {
                villager = true;
                break;
            }
        }
        if(villager){
            event.setCancelled(true);
        }
    }
}
