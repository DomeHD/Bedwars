package de.elwiron.bedwars.listener;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.gamemodes.Victory;
import de.elwiron.bedwars.manager.scoreboard.ScoreboardManagerBedwars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.api.StatsAPI;
import de.elwiron.mangemant.spigot.events.PlayerJoin;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class PlayerDeath implements Listener {


    public PlayerDeath(BedWars plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void Death(PlayerDeathEvent e) {

        if(BedWars.getInstance().getState() == GameState.LOBBY) {
            e.getEntity().teleport(Join.spawn);
        }else {
            Player killer = e.getEntity().getKiller();
            Player p = e.getEntity();

    
            
            p.setFireTicks(0);
            if (killer != null) {
                e.setDeathMessage(BedWars.getInstance().getPrefix() + "Der Spieler " +PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7wurde von " + PlayerJoin.sb.getPlayerTeam(killer).getPrefix() + killer.getName() + " §7getötet");
                killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
                p.sendTitle(PlayerJoin.sb.getPlayerTeam(killer).getPrefix() + killer.getName(),"§aHat Dich getötet");
                StatsAPI.getPlayerStats().get(killer.getUniqueId()).addKills(1);
            } else {
                e.setDeathMessage(BedWars.getInstance().getPrefix() + "Der Spieler " + PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7ist gestorben!");
                p.sendTitle("§cSelbstmord","§aDu hast dich selbst getötet");
            }


            p.playSound(p.getLocation(), Sound.GHAST_DEATH, 1,1 );
            StatsAPI.getPlayerStats().get(p.getUniqueId()).addDeath(1);
            
            
            if(Teams.getTeamByPlayer(p).isRespawn()){
                //
                e.getDrops().clear();
                p.playSound(p.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
             
                sendSpawnPacket(p);
            }else {
                e.getDrops().clear();
                
                if(killer != null) {
             	   PlayerAPI.getPlayerTypes().get(killer.getUniqueId()).getCoins().addCoins(5);
             	   killer.sendMessage(BedWars.getInstance().getPrefix() + "Du hast §e5 §7Coins Für das Tötet von " + PlayerJoin.sb.getPlayerTeam(p).getPrefix() + p.getName() + " §7Bekommen!");
                }
                
                Teams te = Teams.getTeamByPlayer(p);
                PlayerJoin.sb.getTeam(PlayerJoin.sb.getPlayerTeam(p).getName()).removePlayer(p);
                te.getPlayers().remove(p);

                if(te.getPlayers().isEmpty()){
                    te.setLife(false);
                }

                Teams t = checkWin();
                sendSpawnPacket(p);
                 p.teleport(Teams.RED.getSpawn());
                if(t == null){
                    setSpec(p, false);
                }else {
                    Teams.SPECTATOR.getPlayers().add(p);
                    PlayerJoin.sb.getTeam(Teams.SPECTATOR.getName()).addPlayer(p);

                    Victory.OBJ.victoryBuild(t, null);
                }
            }

            
            
            for(Player all : Bukkit.getOnlinePlayers()){
               new ScoreboardManagerBedwars(all).updateScoreboard(all);
            }


        }
    }


    public static void setSpec(final Player p, boolean newjoin) {
        p.setAllowFlight(true);

        Teams.SPECTATOR.getPlayers().add(p);
        PlayerJoin.sb.getTeam(Teams.SPECTATOR.getName()).addPlayer(p);


        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(PlayerJoin.sb);
            all.hidePlayer(p);
            if(Teams.isSpectator(all)){
                p.hidePlayer(all);
            }
        }

        // --- Armor Setting ---//

        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.getInventory().clear();

        // --- Inventory Setting ---//

        p.setHealth(20);
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), new Runnable() {
            public void run() {
                p.getInventory().setItem(0, CreateIteam(Material.COMPASS, "§bTeleportieren"));
                p.getInventory().setItem(8, CreateIteam(Material.SLIME_BALL, "§cLobby"));
                p.teleport(Teams.RED.getSpawn());
            }
        }, 40);



    }

    public static Teams checkWin(){

        int i = 0;
        Teams wt = null;
        for(Teams t : Teams.values()){
            if(t.isAlive()){
                if(t.isLife()){
                    i++;
                    wt = t;
                }
            }
        }

        if(i == 1) return wt;
        return null;
    }

    private static ItemStack CreateIteam(Material m, String Name) {

        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        item.setItemMeta(meta);

        return item;
    }

	public static void sendSpawnPacket(final Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(BedWars.getInstance(), new Runnable() {
			public void run() {
				CraftPlayer player = (CraftPlayer) p;
				PacketPlayInClientCommand packet = new PacketPlayInClientCommand();
				try {
					Field a = PacketPlayInClientCommand.class.getDeclaredField("a");
					a.setAccessible(true);
					a.set(packet, PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
					player.getHandle().playerConnection.a(packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
				player.getHandle().playerConnection.a(packet);
			}
		}, 5);
	}


}
