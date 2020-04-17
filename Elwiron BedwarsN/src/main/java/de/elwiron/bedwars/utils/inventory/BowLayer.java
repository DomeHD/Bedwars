package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum BowLayer {

    BOW1("Bow  ", "§6Bow 1", CreateIteam(Material.BOW, "§6Bow 1", "§e3 Gold"), "§e3 Gold", getBow1(), 3, DropItems.GOLD),
    BOW2("Bow 2", "§6Bow 2", CreateIteam(Material.BOW, "§6Bow 2",  "§e6 Gold"), "§e6 Gold",getBow2(), 6, DropItems.GOLD),
    BOW3("Bow 3", "§6Bow 3", CreateIteam(Material.BOW, "§6Bow 3", "§e14 Gold"),  "§e14 Gold",getBow3(), 14, DropItems.GOLD),

    ARROW("Pfeil", "§6Pfeil", CreateIteam(Material.ARROW, "§6Pfeil", "§e1 Eisen"), "§e1 Eisen", new ArrayList<Enchantment>(), 1, DropItems.IRON);

    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private ArrayList<Enchantment> enchantment;
    private int cost;
    private DropItems currency;

    BowLayer(String name, String displayName,ItemStack itemStack,String lore, ArrayList<Enchantment> enchantment, int cost, DropItems currency){
        this.name = name;
        this.displayName = displayName;
        this.itemStack = itemStack;
        this.enchantment = enchantment;
        this.cost = cost;
        this.currency = currency;
        this.lore = lore;
    }

    private static ItemStack CreateIteam(Material m, String Name,String l) {

        ArrayList<String> lore = new ArrayList<>();
        lore.add(l);

        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }


    private static ArrayList<Enchantment> getBow1(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.ARROW_INFINITE);

        return ae;
    }
    private static ArrayList<Enchantment> getBow2(){
        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.ARROW_INFINITE);
        ae.add(Enchantment.ARROW_KNOCKBACK);

        return ae;
    }
    private static ArrayList<Enchantment> getBow3(){
        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.ARROW_INFINITE);
        ae.add(Enchantment.ARROW_KNOCKBACK);
        ae.add(Enchantment.ARROW_FIRE);
        return ae;
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
		for (BowLayer al : BowLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {
					ItemStack it = new ItemStack(al.getItemStack());

					ItemMeta im = it.getItemMeta();
					im.setLore(new ArrayList<String>());
					it.setItemMeta(im);


					for(Enchantment enchantment : al.getEnchantment()){
						it.addEnchantment(enchantment, 1);
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
