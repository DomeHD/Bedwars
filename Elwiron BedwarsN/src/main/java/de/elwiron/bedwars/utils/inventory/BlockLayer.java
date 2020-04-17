package de.elwiron.bedwars.utils.inventory;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.inventory.BlockInventroy;
import de.elwiron.bedwars.listener.InventoryClick;
import de.elwiron.bedwars.utils.DropItems;
import de.elwiron.bedwars.utils.Teams;

import java.util.ArrayList;

public enum BlockLayer  {


    BLOCK("Clay", "§6Clay", CreateIteam(Material.STAINED_CLAY, "§6Clay", "§c1 Bronze", 1), "§c1 Bronze",2 , 1, DropItems.BRONZE),
    WOOL("Wolle", "§6Wolle", CreateIteam(Material.WOOL, "§6Wolle", "§c1 Bronze", 1), "§c1 Bronze",4 , 1, DropItems.BRONZE),
    
    EISENBLOCK("Eisenblock", "§7Eisenblock", CreateIteam(Material.IRON_BLOCK, "§7Eisenblock", "§74 Eisen", 1), "§74 Eisen",1, 4, DropItems.IRON),

    ENDERBLOCK("Enderstein", "§6Enderstein", CreateIteam(Material.ENDER_STONE, "§6Enderstein", "§c8 Bronze", 1), "§c8Bronze",1, 8, DropItems.BRONZE);


    private String name;
        private String displayName;
        private ItemStack itemStack;
        private String lore;
        private int stack;
        private int cost;
        private DropItems currency;

    BlockLayer(String name, String displayName,ItemStack itemStack,String lore, int enchantment, int cost, DropItems currency){
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

        public ItemStack getItemStack() {
            return itemStack;
        }
        public String getDisplayName() {
            return displayName;
        }
        public int getStack(){
            return stack;
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
		for (BlockLayer al : BlockLayer.values()) {
			if (al.getDisplayName().equals(is.getItemMeta().getDisplayName())) {
				if(al.getStack() != is.getAmount()){
					continue;
				}
				if (p.getInventory().contains(al.getCurrency().getMaterial(), al.getCost())) {

					if(al.getDisplayName().equals(BlockLayer.BLOCK.getDisplayName())){
						p.getInventory().addItem(InventoryClick.setColorBlocks(al.getItemStack().getType(), al.getDisplayName(), al.getLore(), Teams.getTeamByPlayer(p).getColor(), al.getStack()));
					} else if (al.getDisplayName().equals(BlockLayer.WOOL.getDisplayName())) {
						p.getInventory().addItem(InventoryClick.setColorBlocks(al.getItemStack().getType(), al.getDisplayName(), al.getLore(), Teams.getTeamByPlayer(p).getColor(), al.getStack()));
					}else {
						ItemMeta im = al.getItemStack().getItemMeta();
						im.setLore(new ArrayList<String>());
						
						ItemStack it = al.getItemStack();
						it.setItemMeta(im);
						
						p.getInventory().addItem(it);
					}

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
							if (cost >= 0) {
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
