package de.elwiron.bedwars.utils;


import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public enum Teams {


    RED("Rot", "§4Rot", "§4",DyeColor.RED, new ArrayList<Player>(),true, null, null),
    GREEN("Grün", "§aGrün", "§a",DyeColor.GREEN, new ArrayList<Player>(), true ,null, null),
    BLUE("Blau", "§9Blau", "§9",DyeColor.BLUE,  new ArrayList<Player>(),true,null, null),
    YELLOW("Gelb", "§eGelb","§e",DyeColor.YELLOW, new ArrayList<Player>(), true,null, null),
    BLACK("Schwars", "§0Schwars", "§0", DyeColor.BLACK,new ArrayList<Player>(),true,null, null),
    GOLD("Gold", "§6Gold", "§6", DyeColor.ORANGE,new ArrayList<Player>(),true,null, null),
    AQUA("Aqua", "§bAqua","§b", DyeColor.LIGHT_BLUE,new ArrayList<Player>(), true,null, null),
    PURPLE("Lia", "§5Lila", "§5", DyeColor.PURPLE, new ArrayList<Player>(),true,null, null),

    SPECTATOR("Spectator", "§7Spectator", "§7", DyeColor.GRAY,  new ArrayList<Player>(), false,null, null),
    NOTEAM("NOTEAM", "§7Kein Team", "§7", DyeColor.GRAY, new ArrayList<Player>(), false,null, null);

     Teams(String name, String displayName, String prefix, DyeColor color, ArrayList<Player> players,Boolean alive, Location spawn, Location bed){
         this.name = name;
         this.displayName = displayName;
         this.prefix = prefix;
         this.players = players;
         this.alive = alive;
         this.spawn = spawn;
         this.bed = bed;
         this.color = color;
         this.respawn = true;
         this.life = true;
    }

    private String name;
    private final String displayName;
    private final String prefix;
    private final DyeColor color;
    private final ArrayList<Player> players;

    private final boolean alive;

    private Location spawn;
    private Location bed;
    private boolean respawn;
    private boolean life;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        return prefix;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public DyeColor getColor() {
        return color;
    }

    public boolean isAlive() {
        return alive;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getBed() {
        return bed;
    }

    public void setBed(Location bed) {
        this.bed = bed;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }
    public static Teams getTeamByDisplayName(String displayName){
        for(Teams t : Teams.values()){
            if(t.getDisplayName().equals(displayName)){
                return t;
            }
        }
        return null;
    }

    public static Teams getTeamByPlayer(Player p){
        for(Teams t : Teams.values()){
            if(t.players.contains(p)){
                return t;
            }
        }
        return null;
    }
    public static Boolean isSpectator(Player p){
        if(SPECTATOR.getPlayers().contains(p)) return true;
        return false;
    }
}
