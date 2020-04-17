package de.elwiron.bedwars.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.DropItems;

import java.util.ArrayList;

public enum AmorLayer {

    LEATHERHELM("Helm", "§6Helm", CreateIteam(Material.LEATHER_HELMET, "§6Helm", "§c5 Bronze"),"§c5 Bronze", new ArrayList<Enchantment>(), 5, DropItems.BRONZE),
    LEATHERCHESTPLATE("Brustplatte", "§6Brustplatte", CreateIteam(Material.LEATHER_CHESTPLATE, "§6Brustplatte", "§c5 Bronze"),"§c5 Bronze", new ArrayList<Enchantment>(), 5, DropItems.BRONZE),
    LEATHERLEGGINS("Hose", "§6Hose", CreateIteam(Material.LEATHER_LEGGINGS, "§6Hose", "§c5 Bronze"),"§c5 Bronze", new ArrayList<Enchantment>(), 5, DropItems.BRONZE),
    LEATHERBOOTS("Schuhe", "§6Schuhe", CreateIteam(Material.LEATHER_BOOTS, "§6Schuhe", "§c5 Bronze"),"§c5 Bronze", new ArrayList<Enchantment>(), 5, DropItems.BRONZE),

    CHESTPLATE1("Brustplatte", "§6Brustplatte 1", CreateIteam(Material.CHAINMAIL_CHESTPLATE, "§6Brustplatte 1", "§71 Eisen"),"§71 Eisen", getChestplatt(), 1, DropItems.IRON),
    CHESTPLATE2("Brustplatte", "§6Brustplatte 2", CreateIteam(Material.CHAINMAIL_CHESTPLATE, "§6Brustplatte 2", "§73 Eisen"),"§73 Eisen", getChestplatt(), 3, DropItems.IRON),
    CHESTPLATE3("Brustplatte", "§6Brustplatte 3", CreateIteam(Material.CHAINMAIL_CHESTPLATE, "§6Brustplatte 3", "§77 Eisen"),"§77 Eisen", getChestplatt(), 7, DropItems.IRON);

    private String name;
    private String displayName;
    private ItemStack itemStack;
    private String lore;
    private ArrayList<Enchantment> enchantment;
    private int cost;
    private DropItems currency;

    AmorLayer(String name, String displayName,ItemStack itemStack,String lore, ArrayList<Enchantment> enchantment, int cost, DropItems currency){
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

    private static ArrayList<Enchantment> getChestplatt(){

        ArrayList<Enchantment> ae = new ArrayList<Enchantment>();
        ae.add(Enchantment.PROTECTION_ENVIRONMENTAL);

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

    public ArrayList<Enchantment> getEnchantment() {
        return enchantment;
    }

    public int getCost() {
        return cost;
    }

    public DropItems getCurrency() {
        return currency;
    }

    public String getLore() {
        return lore;
    }

    public static void BuyItems(Player p, ItemStack is, boolean isNotify) {
		for (AmorLayer al : AmorLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {

					ItemStack it = new ItemStack(al.getItemStack());

					ItemMeta im = it.getItemMeta();
					im.setLore(new ArrayList<String>());
					it.setItemMeta(im);

					for(Enchantment enchantment : al.getEnchantment()){
						it.addEnchantment(enchantment, Integer.parseInt(al.getDisplayName().replace("§6Brustplatte ", "")));
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
							if (cost < 0) {
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
