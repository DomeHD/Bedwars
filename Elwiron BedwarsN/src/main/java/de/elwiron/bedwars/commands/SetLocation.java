package de.elwiron.bedwars.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class SetLocation implements CommandExecutor {

    private BedWars plugin;

    public SetLocation(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setlocation")) {

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
                if (args[1].equalsIgnoreCase("Rot")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.red.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team Rot auf der Map §e" + name);

                }else if (args[1].equalsIgnoreCase("Blau")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.blue.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team Blau auf der Map §e" + name);
                }else if (args[1].equalsIgnoreCase("Grun")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.green.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team Grün auf der Map §e" + name);
                }else if (args[1].equalsIgnoreCase("Gelb")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.yellow.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team yellow auf der Map §e" + name);

                    //new
                }else if (args[1].equalsIgnoreCase("schwarz")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.black.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team black auf der Map §e" + name);
                }else if (args[1].equalsIgnoreCase("Gold")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.gold.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team gold auf der Map §e" + name);
                }else if (args[1].equalsIgnoreCase("Aqua")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.aqua.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team aqua auf der Map §e" + name);
                }else if (args[1].equalsIgnoreCase("Lila")) {
                    String name = args[0];
                    Location loc = p.getLocation();


                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.X", loc.getBlockX());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.Y", loc.getBlockY());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.Z", loc.getBlockZ());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.yaw", loc.getYaw());
                    ConfigCreator.location.set("Game.map." + name + ".spawn.Purple.pitch", loc.getPitch());
                    ConfigCreator.location.saveConfig();
                    p.sendMessage(plugin.getPrefix() + "Die Location wurde zum spawn von Team Purple auf der Map §e" + name);
                }else {
                    p.sendMessage(plugin.getPrefix() + "Sc/setLocation [name] [Rot/Grun/Blau/Gelb/schwarz/Gold/Aqua/Gray/Lila]");
                }
            }else {
                p.sendMessage(plugin.getPrefix() + "Sc/setLocation [name] [Rot/Grun/Blau/Gelb/schwarz/Gold/Aqua/Gray/Lila]");
            }
        }


        return true;
    }
}

