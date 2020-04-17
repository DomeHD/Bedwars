package de.elwiron.bedwars.gamemodes;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.manager.SpawnerManager;
import de.elwiron.bedwars.utils.Teams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class InitWorld {

	public static final InitWorld OBJ = new InitWorld();
    private World gameworld;

    public  void gameworld() {

        String name = LobbyTime.OBJ.getPlayMap();
        String world = ConfigCreator.location.getString("Game.map." + LobbyTime.OBJ.getPlayMap() + ".world");

        File or = new File("../../../Maps/Bedwars/" + world);
        File tagetOr = new File(BedWars.getInstance().getDataFolder() + "/../../" + world);

        Path moveSourcePath = Paths.get( "../../../Maps/Bedwars/" + world);
        Path moveTargetPath = Paths.get( BedWars.getInstance().getDataFolder() + "/../../" + world);
        try{
            Files.copy( moveSourcePath, moveTargetPath, StandardCopyOption.REPLACE_EXISTING);
            copyFilesInDirectory(or, tagetOr);

        }catch (IOException e){
            Bukkit.getConsoleSender().sendMessage(BedWars.getInstance().getPrefix() + "§cEs ist fehler eim Kopien aufgetretten!");
        }




        WorldCreator creator = new WorldCreator(world);
        creator.createWorld();
        Bukkit.getWorld(world).setDifficulty(Difficulty.EASY);
        Bukkit.getWorld(world).setAmbientSpawnLimit(10);
        Bukkit.getWorld(world).setMonsterSpawnLimit(0);
        gameworld = Bukkit.getWorld(world);
        for(Entity en : gameworld.getEntities()){
            if(en.getType() == EntityType.VILLAGER || en instanceof Player){
                continue;
            }
            en.remove();
        }
        
       

        //give a Spawn Location
        Teams.RED.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.pitch")));
        Teams.GOLD.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.pitch")));
        Teams.YELLOW.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.pitch")));
        Teams.GREEN.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.pitch")));
        Teams.BLUE.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.pitch")));
        Teams.AQUA.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.pitch")));
        Teams.PURPLE.setSpawn( new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.Z"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.yaw"),ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.pitch")));


        //give a bed Location
        Location loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.black.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.black.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.black.bed.Z"));
        Teams.BLACK.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.red.bed.Z"));
        Teams.RED.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.gold.bed.Z"));
        Teams.GOLD.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.yellow.bed.Z"));
        Teams.YELLOW.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.green.bed.Z"));
        Teams.GREEN.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.blue.bed.Z"));
        Teams.BLUE.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.aqua.bed.Z"));
        Teams.AQUA.setBed(loc);

        loc = new Location(Bukkit.getWorld(world), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.bed.X"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.bed.Y"), ConfigCreator.location.getInt("Game.map." + name + ".spawn.Purple.bed.Z"));
        Teams.PURPLE.setBed(loc);
    }

    private void copyFilesInDirectory(File from, File to) throws IOException {
        if(!to.exists()) {
            to.mkdirs();
        }
        for (File file : from.listFiles()) {
            if (file.isDirectory()) {
                copyFilesInDirectory(file, new File(to.getAbsolutePath() + "/" + file.getName()));
            } else {
                File n = new File(to.getAbsolutePath() + "/" + file.getName());
                Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }



    public  World getGameworld() {
        return gameworld;
    }
}
