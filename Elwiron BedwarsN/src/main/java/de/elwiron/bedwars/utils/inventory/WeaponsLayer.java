package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum WeaponsLayer {


    STICK("Stick", "§6Stick", CreateIteam(Material.STICK, "§6Stick", "§c7 Bronze"), "§c7 Bronze", getStick(), 7, DropItems.BRONZE),

    SWORD1("Stein Schwert", "§6Stein Schwert 1", CreateIteam(Material.STONE_SWORD, "§6Stein Schwert 1", "§71 Eisen"),"§71 Eisen", getSword(), 1, DropItems.IRON),
    SWORD2("Stein Schwert", "§6Stein Schwert 2", CreateIteam(Material.STONE_SWORD, "§6Stein Schwert 2", "§73 Eisen"), "§73 Eisen", getSword(), 3, DropItems.IRON),
    SWORD3("Stein Schwert", "§6Stein Schwert 3", CreateIteam(Material.STONE_SWORD, "§6Stein Schwert 3", "§75 Eisen"), "§75 Eisen", getSword(), 5, DropItems.IRON),

    SWORDIRON("Eisen Schwert", "§6Eisen Schwert", CreateIteam(Material.IRON_SWORD, "§6Eisen Schwert", "§e5 Gold"), "§e5 Gold", getSwordIron(), 5, DropItems.GOLD);


    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private ArrayList<Enchantment> enchantment;
    private int cost;
    private DropItems currency;

    WeaponsLayer(String name, String displayName,ItemStack itemStack,String lore, ArrayList<Enchantment> enchantment, int cost, DropItems currency){
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

        private static ArrayList<Enchantment> getStick(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.KNOCKBACK);

        return ae;
      }

    private static ArrayList<Enchantment> getSword(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.DAMAGE_ALL);

        return ae;
    }
    private static ArrayList<Enchantment> getSwordIron(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.DAMAGE_ALL);
        ae.add(Enchantment.KNOCKBACK);

        return ae;
    }


        public String getName() {
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
		for (WeaponsLayer al : WeaponsLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {
					ItemStack it = new ItemStack(al.getItemStack());


					if(al == WeaponsLayer.SWORDIRON){
						for(Enchantment enchantment : al.getEnchantment()){
							it.addEnchantment(enchantment, 1);
						}
					}else if(al == WeaponsLayer.STICK){
						for(Enchantment enchantment : al.getEnchantment()){
							ItemMeta im = it.getItemMeta();
							im.addEnchant(enchantment, 1, true);
							im.setDisplayName(it.getItemMeta().getDisplayName());
							it.setItemMeta(im);
						}
					}else{
						for(Enchantment enchantment : al.getEnchantment()){
							it.addEnchantment(enchantment, Integer.parseInt(al.getDisplayName().replace("§6Stein Schwert ", "")));
						}
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
