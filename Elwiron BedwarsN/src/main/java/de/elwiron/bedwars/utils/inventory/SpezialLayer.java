package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum SpezialLayer {

    LADDER("Leiter", "§6Leiter", CreateIteam(Material.LADDER, "§6Leiter", "§c3 Bronze", 1), "§c3 Bronze", 1, 3, DropItems.BRONZE),
    ENDERPEARL("Ender Perle", "§6Ender Perle", CreateIteam(Material.ENDER_PEARL, "§6Ender Perle", "§e15 Gold", 1), "§e15 Gold", 1, 15, DropItems.GOLD),

    BLAZEROD("Rettungs insel", "§6Rettungs insel", CreateIteam(Material.BLAZE_ROD, "§6Rettungs insel", "§e3 Gold", 1), "§e3 Gold", 1, 3, DropItems.GOLD),
    HOME("Home", "§6Home", CreateIteam(Material.getMaterial(289), "§6Home", "§75 Eisen", 1), "§75 Eisen", 1, 5, DropItems.IRON),

    CHEST("Kiste", "§6Kiste", CreateIteam(Material.CHEST, "§6Kiste", "§71 Eisen", 1), "§71 Eisen", 1, 1, DropItems.IRON),
    
	ANGEL("Angel", "§6Angel", CreateIteam(Material.FISHING_ROD , "§6Angel", "§73 Eisen", 1), "§73 Eisen", 1, 3, DropItems.IRON),
	FEUERZEUG("Feuerzeug", "§6Feuerzeug", CreateIteam(Material.FLINT_AND_STEEL , "§6Feuerzeug", "§71 Eisen", 1), "§71 Eisen", 1, 1, DropItems.IRON),
	
	WEBEN("Spinnen-weben", "§6Spinnen-weben", CreateIteam(Material.WEB , "§6Spinnen-weben", "§c8 Bronze", 1), "§c8 Bronze", 1, 8, DropItems.BRONZE),
	TNT("TNT", "§6TNT", CreateIteam(Material.TNT , "§6TNT", "§e1 Gold", 1), "§e1 Gold", 1, 1, DropItems.GOLD),
	
	ENDERCHEST("ender truhe", "§6Ender Truhe", CreateIteam(Material.ENDER_CHEST , "§6Ender Truhe", "§e1 Gold", 1), "§e1 Gold", 1, 1, DropItems.GOLD);
	
    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private int stack;
    private int cost;
    private DropItems currency;

    SpezialLayer(String name, String displayName,ItemStack itemStack,String lore, int enchantment, int cost, DropItems currency){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
        this.stack = enchantment;
        this.cost = cost;
        this.lore = lore;
        this.currency = currency;
    }
    

    private static ItemStack CreateIteam(Material m, String Name, String l, int size) {
    	ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack item = new ItemStack(m);
        item.setAmount(size);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getLore() {
        return lore;
    }

    public int getStack() {
        return stack;
    }

    public int getCost() {
        return cost;
    }

    public DropItems getCurrency() {
        return currency;
    }

    public static void BuyItems(Player p, ItemStack is, boolean isNotify) {
		for (SpezialLayer al : SpezialLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if(al.getStack() != is.getAmount()){
					continue;
				}
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {
					p.getInventory().addItem(al.getItemStack());
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
