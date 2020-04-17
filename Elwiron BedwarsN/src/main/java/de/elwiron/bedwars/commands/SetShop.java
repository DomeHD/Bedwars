package de.elwiron.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class SetShop implements CommandExecutor {

    private de.elwiron.bedwars.BedWars plugin;

    public SetShop(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setshop")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getPrefix() + "§cNur Spieler Dürfen /setLocation nutzen");
                return true;
            }
            Player p = (Player) sender;

			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN))) {
				p.sendMessage(BedWars.getInstance().getNoperm());
				return true;
			}

            if (BedWars.getInstance().getState() != GameState.MAINTENANCE) {
                p.sendMessage(plugin.getPrefix() + "§cnur im maintenance mode möglich!");
                return true;
            }


            if (args.length == 1) {
                int shop;
                try {
                    shop = ConfigCreator.shop.getInt("Shop.list." + args[0] + ".shops");
                } catch (Exception e) {
                    shop = 1;
                }
                shop++;
                ConfigCreator.shop.set("Shop.list." + args[0] + "." + shop + ".X", p.getLocation().getBlockX());
                ConfigCreator.shop.set("Shop.list." + args[0] + "." + shop + ".Y", p.getLocation().getBlockY());
                ConfigCreator.shop.set("Shop.list." + args[0] + "." + shop + ".Z", p.getLocation().getBlockZ());
                ConfigCreator.shop.set("Shop.list." + args[0] + ".shops", shop);
                ConfigCreator.shop.saveConfig();

                p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast den Shop gesetzt!");
            } else {
                p.sendMessage(plugin.getPrefix() + "Sc/setspawner [name]");
            }
        }
        return true;
    }
}
