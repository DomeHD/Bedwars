package de.elwiron.bedwars.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.gamemodes.Victory;
import de.elwiron.bedwars.manager.scoreboard.ScoreboardManagerBedwars;

public class GameTimeManager {

    static int time = 60 * 60;

    public static void updateTime(){

        new BukkitRunnable() {
            @Override
            public void run() {
                time--;
                for(Player all : Bukkit.getOnlinePlayers()){
                    new ScoreboardManagerBedwars(all).updateScoreboard(all);
                }
                if(time == 0){
                    Victory.OBJ.victoryBuild(null, null);
                }
            }
        }.runTaskTimer(BedWars.getInstance(),  0, 20);


    }

    public void setTime(int time) {
        this.time = time;
    }
}
