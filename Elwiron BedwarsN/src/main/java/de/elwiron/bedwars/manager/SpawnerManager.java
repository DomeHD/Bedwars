package de.elwiron.bedwars.manager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.gamemodes.InitWorld;
import de.elwiron.bedwars.utils.DropItems;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpawnerManager {


    private static ArrayList<Location> bronze = null;
    private static ArrayList<Location> iron = null;
    private static ArrayList<Location> gold = null;

    public static void spawnItem(String mapName){

        if(bronze == null){
            bronze = new ArrayList<>();
            for(int i = 1; i <= ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Bronze.spawner"); i++ ){
                Location l = new Location(InitWorld.OBJ.getGameworld(), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Bronze."+ i +".X") + 0.5, ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Bronze."+ i +".Y"), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Bronze."+ i +".Z") + 0.5);
                bronze.add(l);
            }
        }
        if(iron == null){
            iron = new ArrayList<>();
            for(int i = 1; i <= ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Eisen.spawner"); i++ ) {
                Location l = new Location(InitWorld.OBJ.getGameworld(), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Eisen."+ i +".X")+ 0.5, ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Eisen."+ i +".Y"), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Eisen."+ i +".Z")+ 0.5);
                iron.add(l);
            }
        }
        if(gold == null){
            gold = new ArrayList<>();
            for(int i = 1; i <= ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Gold.spawner"); i++ ) {
                Location l = new Location(InitWorld.OBJ.getGameworld(), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Gold."+ i +".X")+ 0.5, ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Gold."+ i +".Y"), ConfigCreator.spawner.getInt("Maps.list." + mapName + ".Gold."+ i +".Z")+ 0.5);
                gold.add(l);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : bronze) {
                    location.getWorld().dropItem(location, CreateIteam(DropItems.BRONZE.getMaterial(), DropItems.BRONZE.getDisplayName())).setVelocity(new Vector(0, 0.3, 0));
                }
            }
        }.runTaskTimer(BedWars.getInstance(), 1, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Location location : iron) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            location.getWorld().dropItem(location, CreateIteam(DropItems.IRON.getMaterial(), DropItems.IRON.getDisplayName())).setVelocity(new Vector(0, 0.3, 0));
                        }
                    }.runTask(BedWars.getInstance());
                }
            }
        }.runTaskTimerAsynchronously(BedWars.getInstance(), 20 * 20, 5 * 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Location location : gold) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            location.getWorld().dropItem(location, CreateIteam(DropItems.GOLD.getMaterial(), DropItems.GOLD.getDisplayName())).setVelocity(new Vector(0, 0.3, 0));
                        }
                    }.runTask(BedWars.getInstance());
                }
            }
        }.runTaskTimerAsynchronously(BedWars.getInstance(), 30 * 20, 15 * 20);



    }

    private static ItemStack CreateIteam(Material m, String Name) {

        ItemStack item = new ItemStack(m);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        item.setItemMeta(meta);

        return item;
    }

}
