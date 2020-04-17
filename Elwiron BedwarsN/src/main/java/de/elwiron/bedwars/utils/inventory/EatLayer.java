package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum EatLayer {

    BEEF("Steack", "§6Steak", CreateIteam(Material.COOKED_BEEF, "§6Steak", "§c1 Bronze", 1), "§c1 Bronze", 1, 1, DropItems.BRONZE),

    CAKE("Kuchen", "§6Kuchen", CreateIteam(Material.CAKE, "§6Kuchen", "§71 Eisen", 1),"§71 Eisen", 1, 1, DropItems.IRON),
    APPLE("Gold Apfel", "§6Gold Apfel", CreateIteam(Material.GOLDEN_APPLE, "§6Gold Apfel", "§e2 Gold", 1),"§eGold Apfel", 1, 2, DropItems.GOLD),
    
    CHICK("Hänchen", "§6Hänchen", CreateIteam(Material.COOKED_CHICKEN, "§6Hänchen", "§c1 Bronze", 1),"§c1 Bronze", 1, 1, DropItems.BRONZE);




    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private int stack;
    private int cost;
    private DropItems currency;

    EatLayer(String name, String displayName,ItemStack itemStack,String lore, int enchantment, int cost, DropItems currency){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
        this.stack = enchantment;
        this.cost = cost;
        this.lore = lore;
        this.currency = currency;
    }

    private static ItemStack CreateIteam(Material m, String Name,String l, int size) {

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
		for (EatLayer al : EatLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if(al.getStack() != is.getAmount()){
					continue;
				}
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {


					ItemStack it = new ItemStack(al.getItemStack());
					
					ItemMeta im = it.getItemMeta();
					im.setLore(new ArrayList<String>());
					it.setItemMeta(im);

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
