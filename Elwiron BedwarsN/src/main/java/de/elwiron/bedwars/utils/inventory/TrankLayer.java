package de.elwiron.bedwars.utils.inventory;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

public enum TrankLayer {
	
    SPRUNG("Sprung-kraft", "§6Sprung-kraft", CreateIteam(373, 8235, "§6Sprung-kraft", "§78 Eisen"), "§78 Eisen", 8, DropItems.IRON),
	SPEED("Schnelligkeit", "§6Schnelligkeit", CreateIteam(373, 8226, "§6Schnelligkeit", "§78 Eisen"), "§78 Eisen", 8, DropItems.IRON),
	UNSICHTBAR("Unsichtbar", "§6Unsichtbar", CreateIteam(373, 8238, "§6Unsichtbar", "§e32 Gold"), "§e32 Gold", 32, DropItems.GOLD),
	
	STARKE("Stärke", "§6Stärke", CreateIteam(373, 8233, "§6Stärke", "§e6 Gold"), "§e6 Gold", 6, DropItems.GOLD),
	FIRE("Feuer-Wiedersatnd", "§6Feuer-Wiedersatnd", CreateIteam(373, 8227, "§6Feuer-Wiedersatnd", "§e3 Gold"), "§e3 Gold", 3, DropItems.GOLD),
	REGENARTION("Heilung (Reg)", "§6Heilung (Reg)", CreateIteam(373, 8225, "§6Heilung (Reg)", "§e16 Gold"), "§e16 Gold", 16, DropItems.GOLD);
	
    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private int cost;
    private DropItems currency;


    TrankLayer(String name, String displayName,ItemStack itemStack,String lore, int cost, DropItems currency){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
        this.cost = cost;
        this.currency = currency;
        this.lore = lore;
    }
    
    private static ItemStack CreateIteam(int id,int subId, String Name,String l) {

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack item = new ItemStack(id, 1, (short) subId);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public String getName(){
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public String getDisplayName() {
        return displayName;
    }

    public String getLore() {
        return lore;
    }

    public int getCost() {
        return cost;
    }

    public DropItems getCurrency() {
        return currency;
    }

    public static void BuyItems(Player p, ItemStack is, boolean isNotify) {
		for (TrankLayer al : TrankLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {
					ItemStack it = new ItemStack(al.getItemStack());


					p.getInventory().addItem(it);
					int cost = al.getCost();
					for (ItemStack stack : p.getInventory().getContents()) {
						if (stack == null) {
							continue;
						}
						if (stack.getType() != al.getCurrency().getMaterial()) {
							continue;
						}
						if (!(stack.getAmount() >= cost)) {
							p.getInventory().remove(stack);
							cost -= stack.getAmount();
							if (cost <= 0) {
								break;
							}
							continue;
						}
						if (stack.getAmount() == cost) {
							p.getInventory().remove(stack);
						} else {
							stack.setAmount(stack.getAmount() - cost);
						}
						break;
					}
				} else {
					if(isNotify) p.sendMessage(BedWars.getInstance().getPrefix() + "§cDu hast nicht genug " + al.getCurrency().getDisplayName() + " §cum dir das zu kaufen!");
				}


			}
		}
    }
}
