package de.elwiron.bedwars.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.utils.DropItems;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

import java.util.ArrayList;

public class SetSpawner implements CommandExecutor {

    private BedWars plugin;

    public SetSpawner(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setspawner")) {

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


            if (args.length == 2) {
                //gold eise clay,
                for(DropItems di : DropItems.values()){
                    if(di.getName().equalsIgnoreCase(args[1])){

                        int spawner;
                        try{
                            spawner = ConfigCreator.spawner.getInt("Maps.list." + args[0] + "." + di.getName() + ".spawner");
                        }catch (Exception e){
                            spawner = 1;
                        }
                            spawner++;
                            ConfigCreator.spawner.set("Maps.list." + args[0] + "." + di.getName() + "." + spawner + ".X", p.getLocation().getBlockX());
                            ConfigCreator.spawner.set("Maps.list." + args[0] + "." + di.getName() + "." + spawner + ".Y", p.getLocation().getBlockY());
                            ConfigCreator.spawner.set("Maps.list." + args[0] + "." + di.getName() + "." + spawner + ".Z", p.getLocation().getBlockZ());

                            ConfigCreator.spawner.set("Maps.list." + args[0] + "." + di.getName() + ".spawner", spawner);

                            ConfigCreator.spawner.saveConfig();

                        p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast den " + di.getDisplayName() + " Spawner §agesetzt!");
                        break;
                    }
                }
            }else {
                p.sendMessage(plugin.getPrefix() + "Sc/setspawner [name] [Bronze, Eisen, Gold]");
            }

        }

        return true;
    }
}
