package de.elwiron.bedwars.manager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.gamemodes.InitWorld;

import java.util.ArrayList;

public class ShopSpawnManager {

    private ArrayList<Entity> shops;

    public void spawnShops(String mapName){
        for(int i = 1; i <= ConfigCreator.shop.getInt("Shop.list." + mapName + ".shops"); i++ ){
            Location spawnEntity = new Location(InitWorld.OBJ.getGameworld(), ConfigCreator.shop.getInt("Shop.list." + mapName + "." + i + ".X") + 0.5,  ConfigCreator.shop.getInt("Shop.list." + mapName + "." + i + ".Y"),  ConfigCreator.shop.getInt("Shop.list." + mapName + "." + i + ".Z") + 0.5);
            InitWorld.OBJ.getGameworld().getChunkAt(spawnEntity).load();
            Villager e = (Villager)InitWorld.OBJ.getGameworld().spawnEntity(spawnEntity, EntityType.VILLAGER);
            e.setCustomName("§6Shop");
            e.setCustomNameVisible(true);
            e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 10));
            e.setNoDamageTicks(999999999);
        }

    }



}
