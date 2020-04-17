package de.elwiron.bedwars.commands;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

import java.lang.reflect.Type;
import java.util.Set;

public class SetBed implements CommandExecutor {

    private BedWars plugin;

    public SetBed(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setbed")) {

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
                String name = args[0];

                org.bukkit.block.Block b = p.getTargetBlock((Set<Material>) null, 10);

                if(b.getType() != Material.BED_BLOCK){
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§cDu musst auf ein Bed schauen!");
                    return true;
                }

                if (args[1].equalsIgnoreCase("Rot")) {
                  //  ConfigCreator.location.set("Game.map." + name + ".spawn.red.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Blau")) {
                  //  ConfigCreator.location.set("Game.map." + name + ".spawn.blue.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Grun")) {
                   // ConfigCreator.location.set("Game.map." + name + ".spawn.green.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Gelb")) {
                   // ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("schwarz")) {
                   // ConfigCreator.location.set("Game.map." + name + ".spawn.black.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Gold")) {
                  //  ConfigCreator.location.set("Game.map." + name + ".spawn.gold.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Aqua")) {
                    //ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.bed", b.getLocation());

                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.bed.Z", b.getLocation().getBlockZ());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else if (args[1].equalsIgnoreCase("Lila")) {
                  //  ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.bed", b.getLocation());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.bed.X", b.getLocation().getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.bed.Y", b.getLocation().getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.bed.Z", b.getLocation().getBlockZ());

                    p.sendMessage(BedWars.getInstance().getPrefix() + "§aDu hast das Bett gesetzt!");
                    ConfigCreator.location.saveConfig();
                }else {
                    p.sendMessage(plugin.getPrefix() + "Sc/setbed [name] [Rot/Grun/Blau/Gelb/schwarz/Gold/Aqua/Lila]");
                }


            }else {
                p.sendMessage(plugin.getPrefix() + "Sc/setbed [name] [Rot/Grun/Blau/Gelb/schwarz/Gold/Aqua/Gray/Lila]");
            }
        }

        return true;
    }
}
