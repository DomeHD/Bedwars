package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum PickaxtLayer {

    WOODPICKAXT("Holzspitzhacke", "§6Holzspitzhacke", CreateIteam(Material.WOOD_PICKAXE, "§6Holzspitzhacke", "§c5 Bronze"), "§c8 Bronze", getPickAxt(), 5, DropItems.BRONZE),
    STONEPICKAXT("Steinspitzhacke", "§6Steinspitzhacke", CreateIteam(Material.STONE_PICKAXE, "§6Steinspitzhacke", "§73 Eisen"), "§73 Eisen", getPickAxt(), 3, DropItems.IRON),
    IRONPICKAXT("Eisenspitzhacke", "§6Eisenspitzhacke", CreateIteam(Material.IRON_PICKAXE, "§6Eisenspitzhacke", "§e1 Gold"), "e1 Gold", getPickAxt(), 1, DropItems.GOLD);


    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private ArrayList<Enchantment> enchantment;
    private int cost;
    private DropItems currency;

    PickaxtLayer(String name, String displayName,ItemStack itemStack,String lore, ArrayList<Enchantment> enchantment, int cost, DropItems currency){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
        this.enchantment = enchantment;
        this.cost = cost;
        this.currency = currency;
        this.lore = lore;
    }

    private static ItemStack CreateIteam(Material m, String Name, String l) {

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ArrayList<Enchantment> getPickAxt(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.getById(32));

        return ae;
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

    public ArrayList<Enchantment> getEnchantment() {
        return enchantment;
    }

    public int getCost() {
        return cost;
    }

    public DropItems getCurrency() {
        return currency;
    }

    public static void BuyItems(Player p, ItemStack is, boolean isNotify) {
		for (PickaxtLayer al : PickaxtLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {
					ItemStack it = new ItemStack(al.getItemStack());
					
					ItemMeta im = it.getItemMeta();
					im.setLore(new ArrayList<String>());
					it.setItemMeta(im);


					for(Enchantment enchantment : al.getEnchantment()){
						it.addEnchantment(enchantment, 2);
					}

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
