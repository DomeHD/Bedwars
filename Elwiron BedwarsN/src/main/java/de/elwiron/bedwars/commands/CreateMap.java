package de.elwiron.bedwars.commands;

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

public class CreateMap implements CommandExecutor {

    private BedWars plugin;

    public CreateMap(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("createmap")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cNur Spieler dürfen /createMap nutzen");
                return true;
            }
            Player p = (Player) sender;

			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN))) {
				p.sendMessage(BedWars.getInstance().getNoperm());
				return true;
			}

            if (BedWars.getInstance().getState() != GameState.MAINTENANCE) {
                p.sendMessage(BedWars.getInstance().getPrefix() + "§cnur im maintenance mode möglich!");
                return true;
            }

            if (args.length == 3) {
                String name = args[0];
                String world = args[1];
                int teams;
                try{
                    teams = Integer.parseInt(args[2]);
                }catch (Exception e){
                    teams = 4;
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§cDa du keine gülltige zahl angeben hast gibt es jetzt 4 Teams!");
                }



                if (name.length() >= 20 || world.length() >= 20) {
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§cName und world dürfen maximal 20 Zeichen haben!");
                    return true;
                }

                ConfigCreator.location.set("Game.map." + name + ".world", world);
                ConfigCreator.location.set("Game.map." + name + ".teams", teams);

                for(DropItems di : DropItems.values()){
                    ConfigCreator.spawner.get("Maps.list." + name + "." + di.getName());
                }

                ArrayList<String> worlds = (ArrayList) ConfigCreator.location.get("BedWars.Maps");
                worlds.add(name);
                ConfigCreator.location.set("Game.maps",worlds);
                ConfigCreator.location.saveConfig();
                p.sendMessage(BedWars.getInstance().getPrefix() + "§aDie §e" + name + "§a wurde erstellt!");
            } else {
                p.sendMessage(BedWars.getInstance().getPrefix() + "§c/createMap <Name> <world> <Teams>");
            }

        }
        return true;
    }

}
