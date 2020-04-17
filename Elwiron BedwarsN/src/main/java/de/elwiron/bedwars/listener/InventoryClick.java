package de.elwiron.bedwars.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.inventory.AmorInventory;
import de.elwiron.bedwars.inventory.BlockInventroy;
import de.elwiron.bedwars.inventory.BowInventory;
import de.elwiron.bedwars.inventory.EatInventory;
import de.elwiron.bedwars.inventory.PickAxtInventroy;
import de.elwiron.bedwars.inventory.SpezialInventory;
import de.elwiron.bedwars.inventory.TrankInventory;
import de.elwiron.bedwars.inventory.WeaponsInventory;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.bedwars.utils.inventory.AmorLayer;
import de.elwiron.bedwars.utils.inventory.BlockLayer;
import de.elwiron.bedwars.utils.inventory.BowLayer;
import de.elwiron.bedwars.utils.inventory.EatLayer;
import de.elwiron.bedwars.utils.inventory.PickaxtLayer;
import de.elwiron.bedwars.utils.inventory.ShopLayer;
import de.elwiron.bedwars.utils.inventory.SpezialLayer;
import de.elwiron.bedwars.utils.inventory.TrankLayer;
import de.elwiron.bedwars.utils.inventory.WeaponsLayer;
import de.elwiron.mangemant.spigot.api.CoinsAPI;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.events.PlayerJoin;
import de.elwiron.mangemant.spigot.mysql.Mysql;
import de.elwiron.mangemant.utils.Rang;
import net.md_5.bungee.api.ChatColor;

public class InventoryClick implements Listener {

	public static Plugin plugin;
	
	private static HashMap<String, HashMap<UUID, Player>> team = new HashMap<>();
	
	private static ArrayList<Player> noTeam = new ArrayList<Player>();
	static int maxPlayersTeams = BedWars.getInstance().getMaxPlayersTeam();
	
	private static ArrayList<String> maps = new ArrayList<String>();
	private static HashMap<String, ArrayList<Player>> voteMaps = new HashMap<String, ArrayList<Player>>();
	
	private static Boolean isMapVotig = false;
	private static Boolean isForceMapVotig = true;
	private static String forceMap = null;
	
	
	public InventoryClick(BedWars plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void InventoryC(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
	if(BedWars.getInstance().getState() != GameState.PLAYING) {
		
		if (e.getInventory().getName().equalsIgnoreCase("§bTeams wählen!")) {
			e.setCancelled(true);
			if (!e.getCurrentItem().hasItemMeta()) return;
			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§0") || e.getCurrentItem().getItemMeta().getDisplayName().equals("§cTeams zurücksetzen")){
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cTeams zurücksetzen")){
						for(Player all : Bukkit.getOnlinePlayers()){
							quitAllTeams(all, true);
							all.setScoreboard(PlayerJoin.sb);
							all.sendMessage(BedWars.getInstance().getPrefix() + "Die Teams wurden zurückgesetzt!");
						}
					}
					return;
				}
				quitAllTeams(p, true);
				String targetTeam = e.getCurrentItem().getItemMeta().getDisplayName();
				if (!team.containsKey(targetTeam)) {
					ArrayList<Player> ap = new ArrayList<Player>();
					HashMap<UUID, Player> hp = new HashMap<UUID, Player>();
					hp.put(p.getUniqueId(), p);		
					ap.add(p);
					//team.put(targetTeam, ap);
					team.put(targetTeam, hp);
					
					p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist nun in Team "+ e.getCurrentItem().getItemMeta().getDisplayName());
					for (Teams t : Teams.values()){
						if(t.getDisplayName().equals(targetTeam)){
							PlayerJoin.sb.getTeam(t.getDisplayName()).addPlayer(p);
						}
					}
					for(Player all : Bukkit.getOnlinePlayers()){
					//	new ScoreboardManager().updateScoreboard(all);
						all.setScoreboard(PlayerJoin.sb);
					}
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
					noTeam.remove(p);
					p.closeInventory();
					
				}else if (team.get(targetTeam).size()  < maxPlayersTeams) {
					//team.get(e.getCurrentItem().getItemMeta().getDisplayName()).add(p);
					team.get(e.getCurrentItem().getItemMeta().getDisplayName()).put(p.getUniqueId(), p);
					p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist nun in Team "+ e.getCurrentItem().getItemMeta().getDisplayName());
					for (Teams t : Teams.values()){
						if(t.getDisplayName().equals(targetTeam)){
							PlayerJoin.sb.getTeam(t.getDisplayName()).addPlayer(p);
						}
					}
					for(Player all : Bukkit.getOnlinePlayers()){
						//new ScoreboardManager().updateScoreboard(all);
						all.setScoreboard(PlayerJoin.sb);
					}
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
					noTeam.remove(p);
					p.closeInventory();
				} else {
					if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.PREMIUM)) {
					for (Player all : team.get(targetTeam).values()) {
						if(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHiger(PlayerAPI.getPlayerTypes().get(all.getUniqueId()).getRang())){
							team.get(targetTeam).put(p.getUniqueId(), p);
							
							p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist nun in Team " + e.getCurrentItem().getItemMeta().getDisplayName());
							all.sendMessage(BedWars.getInstance().getPrefix() + "Du wurdes aus Team " + e.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.GRAY +" gekicket");
							
							quitAllTeams(all, true);
							for (Teams t : Teams.values()){
								if(t.getDisplayName().equals(targetTeam)){
									PlayerJoin.sb.getTeam(t.getDisplayName()).addPlayer(p);
								}
							}
							PlayerJoin.sb.getTeam(Teams.NOTEAM.getName()).addPlayer(all);
							for(Player allp : Bukkit.getOnlinePlayers()){
							//	new ScoreboardManager().updateScoreboard(allp);
								allp.setScoreboard(PlayerJoin.sb);
							}
							
							all.playSound(all.getLocation(), Sound.ITEM_BREAK, 1, 1);
							p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
							noTeam.remove(p);
							p.closeInventory();
							return;
						}
						
						}
					}
				
					p.sendMessage(BedWars.getInstance().getPrefix() + "§cDas Team " + e.getCurrentItem().getItemMeta().getDisplayName()+ ChatColor.RED +" ist schon voll!");
					p.closeInventory();
				}
			}
		}else if (e.getInventory().getName().equalsIgnoreCase("§5Force Map")) {
			if (e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§0") || e.getCurrentItem().getItemMeta().getDisplayName().equals("§cKein Map")){
					p.closeInventory();
					return;
				}
			
				forceMap = e.getCurrentItem().getItemMeta().getDisplayName();
				p.sendMessage(BedWars.getInstance().getPrefix() + "§aEs wird §e" + forceMap + " §agespielt!");
		        CloudServer.getInstance().setMotd("§2Map§f: §b" + forceMap);
		        CloudServer.getInstance().update();
		        
				p.closeInventory();
				p.updateInventory();
				return;
			}
		 }
		}else if(BedWars.getInstance().getState() == GameState.PLAYING) {
			if(Teams.getTeamByPlayer(p) == Teams.SPECTATOR){
				e.setCancelled(true);
				if (e.getInventory().getName().equalsIgnoreCase("§7Player")) {
					e.setCancelled(true);
					if (e.getCurrentItem().hasItemMeta()) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
							return;
						}

						Player taget = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
						p.teleport(taget.getLocation());
						p.sendMessage(BedWars.getInstance().getPrefix() + "§7Du beobachtets jetzt: " + PlayerJoin.sb.getPlayerTeam(taget).getPrefix() + taget.getDisplayName());

					}
				}
				return;
			}


			//Shop
			if (e.getInventory().getName().equalsIgnoreCase("§6Shop")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.ARMOR.getDisplayName())) {
						new AmorInventory().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.WEAPONS.getDisplayName())) {
						new WeaponsInventory().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.BOW.getDisplayName())) {
						new BowInventory().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.EAT.getDisplayName())) {
						new EatInventory().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.BLOCKS.getDisplayName())) {
						new BlockInventroy().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.PICKAXT.getDisplayName())) {
						new PickAxtInventroy().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.SPECIAL.getDisplayName())) {
						new SpezialInventory().openInventory(p);
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ShopLayer.TRANK.getDisplayName())) {
						new TrankInventory().openInventory(p);
					}
					


				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Rüstung")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}

					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							AmorLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					AmorLayer.BuyItems(p, e.getCurrentItem(), true);
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Blöcke")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}
	
					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							BlockLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					BlockLayer.BuyItems(p, e.getCurrentItem(), true);

				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Bögen")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}

					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							BowLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					BowLayer.BuyItems(p, e.getCurrentItem(), true);
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Essen")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}
					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							EatLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					EatLayer.BuyItems(p, e.getCurrentItem(), true);
				}


			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Spitzhacke")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}
					
					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							PickaxtLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					PickaxtLayer.BuyItems(p, e.getCurrentItem(), true);

				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Spezial")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}
					
					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							SpezialLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					SpezialLayer.BuyItems(p, e.getCurrentItem(), true);

				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Waffen")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}

					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							WeaponsLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					WeaponsLayer.BuyItems(p, e.getCurrentItem(), true);
				}
			}else if (e.getInventory().getName().equalsIgnoreCase("§6Shop - Trank")) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§0")) {
						return;
					}

					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§bZurück")) {
						PlayerInteractEntity.createMainInv(p);
						return;
					}

					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.RIGHT) {
						for(int i = 0; i < 64; i++) {
							TrankLayer.BuyItems(p, e.getCurrentItem(), false);
							
						}
						return;
					}
					TrankLayer.BuyItems(p, e.getCurrentItem(), true);
				}
			}
		

		}
	}


	
	public static void quitAllTeams(Player p, Boolean hasTeam){
		
		
		
		for(HashMap<UUID, Player> hp : team.values()){
			if(hp.containsKey(p.getUniqueId())){
				hp.remove(p.getUniqueId());
			}
		}
		
		if(hasTeam) {
			PlayerJoin.sb.getPlayerTeam(p).removePlayer(p);
			PlayerAPI.getPlayerTypes().get(p.getUniqueId()).setTabprefix();
			if(!noTeam.contains(p)) noTeam.add(p);
		}
	}

	public static void quitAllTeamsOnLave(Player p){
		for(HashMap<UUID, Player> hp : team.values()){
			if(hp.containsKey(p.getUniqueId())){
				hp.remove(p.getUniqueId());
			}
		}
		
		PlayerJoin.sb.getPlayerTeam(p).removePlayer(p);
	}

	
	public static ItemStack setColorBlocks(Material m, String name, String l, DyeColor color, int size){

		ArrayList<String> lore = new ArrayList<String>();

		ItemStack i = new ItemStack(m, 1, color.getWoolData());
		i.setAmount(size);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}


	public static HashMap<String, HashMap<UUID, Player>> getTeam() {
		return team;
	}

	public static ArrayList<Player> getNoTeam() {
		return noTeam;
	}

	public static int getMaxPlayersTeams() {
		return maxPlayersTeams;
	}

	public static ArrayList<String> getMaps() {
		return maps;
	}

	public static HashMap<String, ArrayList<Player>> getVoteMaps() {
		return voteMaps;
	}

	public static Boolean getIsMapVotig() {
		return isMapVotig;
	}

	public static Boolean getIsForceMapVotig() {
		return isForceMapVotig;
	}

	public static String getForceMap() {
		return forceMap;
	}

	public static void setTeam(HashMap<String, HashMap<UUID, Player>> team) {
		InventoryClick.team = team;
	}

	public static void setNoTeam(ArrayList<Player> noTeam) {
		InventoryClick.noTeam = noTeam;
	}

	public static void setMaxPlayersTeams(int maxPlayersTeams) {
		InventoryClick.maxPlayersTeams = maxPlayersTeams;
	}

	public static void setMaps(ArrayList<String> maps) {
		InventoryClick.maps = maps;
	}

	public static void setVoteMaps(HashMap<String, ArrayList<Player>> voteMaps) {
		InventoryClick.voteMaps = voteMaps;
	}

	public static void setIsMapVotig(Boolean isMapVotig) {
		InventoryClick.isMapVotig = isMapVotig;
	}

	public static void setIsForceMapVotig(Boolean isForceMapVotig) {
		InventoryClick.isForceMapVotig = isForceMapVotig;
	}

	public static void setForceMap(String forceMap) {
		InventoryClick.forceMap = forceMap;
	}
}
