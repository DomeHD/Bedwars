package de.elwiron.bedwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class WorldTP implements CommandExecutor {

    private BedWars plugin;

    public WorldTP(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("worldtp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(BedWars.getInstance().getPrefix() + "§cNur Spieler Dürfen /worldtp nutzen");
                return true;
            }

            Player p = (Player) sender;
            
			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN))) {
				p.sendMessage(BedWars.getInstance().getNoperm());
				return true;
			}

                

                if (args.length == 1) {
                    try{
                        WorldCreator creator = new WorldCreator(args[0]);
                        creator.createWorld();

                        p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
                        p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist jetzt auf Der Welt:§a " + args[0]);
                    }catch(Exception e){
                        p.sendMessage(BedWars.getInstance().getPrefix() + "§cWelt nicht gefunden !");
                    }

                } else {
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§c/worldTp <world>");
                }

            }
        return true;
    }
}
