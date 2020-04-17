package de.elwiron.bedwars.manager.scoreboard;
import org.bukkit.entity.Player;


import java.util.HashMap;

public class ScoreboardManager {

    private static HashMap<Player, ScoreboardManagerBedwars> scoreboards = new HashMap<Player, ScoreboardManagerBedwars>();

    public static void createBoard(Player p) {
        scoreboards.put(p, new ScoreboardManagerBedwars(p));
    }

    public static void removeBoard(Player p) {
        if (hasPlayerScoreboard(p)) {
            scoreboards.get(p).scoreboard.remove();
            scoreboards.remove(p);
        }
    }

    public static void updateBoard(Player p){
        if (hasPlayerScoreboard(p)) {
            scoreboards.get(p).updateScoreboard(p);;
        }
    }

    public static boolean hasPlayerScoreboard(Player p) {
        return scoreboards.containsKey(p);
    }
}