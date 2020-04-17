package de.elwiron.bedwars.gamemodes;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.listener.InventoryClick;
import de.elwiron.bedwars.utils.GameState;
import lombok.Getter;
import lombok.Setter;

public class LobbyTime {

	public static final LobbyTime OBJ = new LobbyTime();
	
	@Getter
	@Setter
	private int contdown = ConfigCreator.cfg.getInt("BedWars.Lobby.Time");
	@Getter
	@Setter
	private Boolean timeGo = false;
	@Getter
	private int s;
	@Getter
	private String playMap;
	@Getter
	private String playModus;

    public  void lobby(Player p) {
        if (Bukkit.getServer().getOnlinePlayers().size() >= ConfigCreator.cfg.getInt("BedWars.MinPlayers")) {
            if (!timeGo) {
                timeGo = true;
                s = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {
                    @SuppressWarnings("deprecation")
                    public void run() {
                        if (contdown > 0) {
                            // Wenn Der contdown läuft | wird das jede Sekunde
                            contdown--;
                            if (contdown == 10) {

                                playMap = BedWars.getInstance().getMapName();
                                if(InventoryClick.getForceMap() != null){
                                    playMap = InventoryClick.getForceMap();
                                }
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(BedWars.getInstance().getPrefix() + "Die Map " + playMap + " wird gespielt!");
                                }

                                InitWorld.OBJ.gameworld();
                            }
                            if (contdown == 5 || contdown == 4 || contdown == 3 || contdown == 2 || contdown == 1) {
                                if (contdown == 3) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle("§5" + playMap, "§ewird gespielt!");
                                    }
                                }
                                if (contdown == 1) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.setLevel(contdown);
                                        p.sendMessage(BedWars.getInstance().getPrefix() + "Spiel startet in §e" + contdown + " §7Sekunde");
                                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                                    }
                                } else {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.setLevel(contdown);
                                        p.sendMessage(BedWars.getInstance().getPrefix() + "Spiel startet in §e" + contdown + " §7Sekunden");
                                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                                    }
                                }
                            } else {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.setLevel(contdown);
                                }
                            }
                        } else {
                            // wenn der contdown zu ende ist !
                            BedWars.getInstance().setState(GameState.PLAYING);
                            Gamestart.OBJ.gamestart();
                            Bukkit.getScheduler().cancelTask(s);
                        }

                    }
                }, 0, 20);

            }
        }

    }

}
