package de.elwiron.bedwars.listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.Timer;
import de.elwiron.mangemant.spigot.api.PlayerAPI;

public class PlayerInteract implements Listener {

	public static Plugin plugin;
	private static ArrayList<String> maps;
	private static HashMap<Player, Timer> homeTimer = new HashMap<>();
	static {
		maps = (ArrayList) ConfigCreator.location.get("BedWars.Maps");
	}

	public PlayerInteract(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerI(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (BedWars.getInstance().getState() == GameState.LOBBY || BedWars.getInstance().getState() == GameState.RESTARTING) {

			if (e.getMaterial() == Material.SLIME_BALL) {
				PlayerAPI.getPlayerTypes().get(p.getUniqueId()).sendToFallback();
			}
			
			if (e.getMaterial() == Material.NETHER_STAR) {
				Bukkit.getServer().dispatchCommand(p, "stats");
			}
			
			if (e.getMaterial() == Material.CHEST) {

				Inventory inv = p.getServer().createInventory(null, 9 * 1, "§bTeams wählen!");

				for(Teams t : BedWars.getInstance().getAktivTeams()){
					if(t.getName().equalsIgnoreCase(Teams.SPECTATOR.getName()) || t.getName().equalsIgnoreCase(Teams.NOTEAM.getName())){
						continue;
					}
					Teams te = Teams.getTeamByDisplayName(t.getDisplayName());
					
					ArrayList<String> lore = new ArrayList<>();
					lore.add("");
					
					if (InventoryClick.getTeam().containsKey(t.getDisplayName())) {
						if(!InventoryClick.getTeam().get(t.getDisplayName()).isEmpty()) {
							for(Player all : InventoryClick.getTeam().get(t.getDisplayName()).values()) {
								lore.add(t.getPrefix() + "► " + all.getName());
							}
							lore.add("");
						}
						
						lore.add(t.getPrefix() + "" +InventoryClick.getTeam().get(t.getDisplayName()).size() + " / " + BedWars.getInstance().getMaxPlayersTeam());
					}else {
						lore.add(t.getPrefix() + "0 / " + BedWars.getInstance().getMaxPlayersTeam());
					}
					
					
					
					
					
					inv.addItem(createWoll(te.getColor(), te.getDisplayName(), lore));

				}
				
				/*if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.MODERATOR)){
					inv.addItem(createItem(399, 0 , "§cTeams zurücksetzen"));
				}*/
				
				p.openInventory(inv);			
				
			}else if (e.getMaterial() == Material.BOOK) {
				e.setCancelled(true);
				if(!InventoryClick.getIsForceMapVotig()){
					p.sendMessage(BedWars.getInstance().getPrefix() + "§cForceMap kann nicht in den Letzten 10 sec. genutzt werden!");
					return;
				}
				Inventory inv = p.getServer().createInventory(null, 9 * 6, "§5Force Map");
				
				
				for(int i = 0; i < 9*6; i++){
					inv.setItem(i, createItem(160, 15, "§0"));
				}
				
				//gitter
				inv.setItem(0, createItem(101, 4, "§0"));
				inv.setItem(8, createItem(101, 4, "§0"));
				inv.setItem(45, createItem(101, 4, "§0"));
				inv.setItem(53, createItem(101, 4, "§0"));
				//helles glas
				inv.setItem(2, createItem(160, 8, "§0"));
				inv.setItem(3, createItem(160, 8, "§0"));
				inv.setItem(4, createItem(160, 8, "§0"));
				inv.setItem(5, createItem(160, 8, "§0"));
				inv.setItem(6, createItem(160, 8, "§0"));
				
				inv.setItem(47, createItem(160, 8, "§0"));
				inv.setItem(48, createItem(160, 8, "§0"));
				inv.setItem(49, createItem(160, 8, "§0"));
				inv.setItem(50, createItem(160, 8, "§0"));
				inv.setItem(51, createItem(160, 8, "§0"));
				//setMaps
				inv.setItem(11, createItem(339, 0, givaMap(0)));
				inv.setItem(12, createItem(339, 0, givaMap(1)));
				inv.setItem(13, createItem(339, 0, givaMap(2)));
				inv.setItem(14, createItem(339, 0, givaMap(3)));
				inv.setItem(15, createItem(339, 0, givaMap(4)));
				inv.setItem(19, createItem(339, 0, givaMap(5)));
				inv.setItem(20, createItem(339, 0, givaMap(6)));
				inv.setItem(21, createItem(339, 0, givaMap(7)));
				inv.setItem(22, createItem(339, 0, givaMap(8)));
				inv.setItem(23, createItem(339, 0, givaMap(9)));
				inv.setItem(24, createItem(339, 0, givaMap(10)));
				inv.setItem(25, createItem(339, 0, givaMap(11)));			
				inv.setItem(28, createItem(339, 0, givaMap(12)));
				inv.setItem(29, createItem(339, 0, givaMap(13)));
				inv.setItem(30, createItem(339, 0, givaMap(14)));
				inv.setItem(31, createItem(339, 0, givaMap(15)));
				inv.setItem(32, createItem(339, 0, givaMap(16)));
				inv.setItem(33, createItem(339, 0, givaMap(17)));
     			inv.setItem(34, createItem(339, 0, givaMap(18)));		
				inv.setItem(38, createItem(339, 0, givaMap(19)));
				inv.setItem(39, createItem(339, 0, givaMap(20)));
				inv.setItem(40, createItem(339, 0, givaMap(21)));
				inv.setItem(41, createItem(339, 0, givaMap(22)));
				inv.setItem(42, createItem(339, 0, givaMap(23)));
				
				
				
				p.openInventory(inv);
			}
		}else if(BedWars.getInstance().getState() == GameState.PLAYING) {

			if (Teams.isSpectator(p)) {

				if (e.getMaterial() == Material.SLIME_BALL) p.kickPlayer("lobby");

				if (e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
					Inventory inv = p.getServer().createInventory(null, 9 * 2, "§7Player");

					for (int i = 0; i < 9 * 2; i++) {
						inv.setItem(i, createItem(160, 15, "§0"));
					}
					int i = 0;
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (p.equals(all)) {
							continue;
						}
						if (Teams.isSpectator(all)) {
							continue;
						}
						if (!(i < 9 * 2)) {
							break;
						}
						inv.setItem(i, createHead(all.getName()));
						i++;
					}
					p.openInventory(inv);
				}

				if (e.getClickedBlock() != null) {
					if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType().equals(Material.ENDER_CHEST) || e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
						e.setCancelled(true);
					}
				}

				return;
			}



			}
			if (e.getMaterial() == Material.BLAZE_ROD) {
				Action a = e.getAction();
				if(p.getItemInHand().getAmount() == 1) {
					p.getInventory().removeItem(p.getItemInHand());
				}else {
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}
				
				
				p.playSound(p.getLocation(), Sound.FALL_BIG, 1.0F, 1.0F);
				World world = p.getWorld();
				Location loc = p.getLocation();
				loc.setY(loc.getY() - 3.0D);
				Block currentBlock = world.getBlockAt(loc);
				currentBlock.setType(setColorBlocks(Teams.getTeamByPlayer(p).getColor()).getType());
				BlockPlace.getBlocklist().add(currentBlock.getLocation());
			}else if (e.getMaterial() == Material.getMaterial(289)) {
				p.sendMessage(BedWars.getInstance().getPrefix() + "Du darfst dich 5 Sekunden nicht bewegen!");

				if(p.getItemInHand().getAmount() == 1) {
					p.getInventory().removeItem(p.getItemInHand());
				}else {
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				}

				homeTimer.put(p, new Timer());
				homeTimer.get(p).setScheduler(Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), new Runnable() {
					@Override
					public void run() {
						if(homeTimer.get(p).getTime() > 0){
							p.sendTitle("§7" + homeTimer.get(p).getTime(),"");
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							homeTimer.get(p).contdown();
						}else {
							Bukkit.getScheduler().cancelTask(homeTimer.get(p).getScheduler());
							homeTimer.remove(p);

							p.teleport(Teams.getTeamByPlayer(p).getSpawn());
							p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist jetzt in deiner base!");
						}
					}
				
				},0, 20));


				
			} 



	}
	
	private static String givaMap(int i){
		if(maps.size() -1 >= i){
			if(ConfigCreator.location.getInt("Game.map." + maps.get(i) + ".teams") == BedWars.getInstance().getMaxTeams()){
				return maps.get(i);
			}else {
				int ii = i;
				for(int iii = i; ConfigCreator.location.getInt("Game.map." + maps.get(ii) + ".teams") != BedWars.getInstance().getMaxTeams(); ii--){}


				return maps.get(ii);
			}

		}
		return "§cKeine Map";

	}
	
	private static ItemStack createItem(int id, int subId, String name) {

		ItemStack i = new ItemStack(id, 1, (short) subId);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);

		return i;
	}
	private static ItemStack createWoll(DyeColor dc, String name, ArrayList<String> lore) {

		ItemStack i = new ItemStack(Material.WOOL, 1, dc.getData());
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	private static ItemStack createHead(String name) {

		ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta im = (SkullMeta) i.getItemMeta();
		im.setOwner(name);
		im.setDisplayName(name);
		i.setItemMeta(im);

		return i;
	}
	public static ItemStack setColorBlocks(DyeColor color){
		return new ItemStack(Material.GLASS, 1, color.getWoolData());
	}

	public static HashMap<Player, Timer> getHomeTimer() {
		return homeTimer;
	}


}
