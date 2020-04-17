package de.elwiron.bedwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

public class LoadWorld implements CommandExecutor {

    private BedWars plugin;

    public LoadWorld(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("loadWorld")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cNur player Dürfen /loadWorld nutzen");
                return true;
            }
            Player p = (Player) sender;
            if (PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isSmaller(Rang.DEVELOPER)) {
                p.sendMessage(BedWars.getInstance().getNoperm());
                return true;
            }

            if (BedWars.getInstance().getState() != GameState.MAINTENANCE) {
                sender.sendMessage(BedWars.getInstance().getPrefix() + "§cnur im maintenance mode möglich!");
                return true;
            }



            if (args.length == 1) {

                File or = new File("../../../Maps/Bedwars/" + args[0]);
                System.out.println(or.getAbsolutePath());
                File tagetOr = new File(BedWars.getInstance().getDataFolder() + "/../../" + args[0]);

                Path moveSourcePath = Paths.get( "../../../Maps/Bedwars/" + args[0]);
                Path moveTargetPath = Paths.get( BedWars.getInstance().getDataFolder() + "/../../" + args[0]);
                try{
                    Files.copy( moveSourcePath, moveTargetPath, StandardCopyOption.REPLACE_EXISTING);
                    copyFilesInDirectory(or, tagetOr);

                    WorldCreator creator = new WorldCreator(args[0]);
                    creator.createWorld();

                    p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
                    p.sendMessage(BedWars.getInstance().getPrefix() + "Du bist jetzt auf Der Welt:§a " + args[0]);
                }catch (IOException e){
                    Bukkit.getConsoleSender().sendMessage(BedWars.getInstance().getPrefix() + "§cEs ist fehler eim Kopien aufgetretten!");
                }
            } else {
                sender.sendMessage(BedWars.getInstance().getPrefix() + "§c/loadworld <world>");
            }

        }
        return true;
    }



    private static void copyFilesInDirectory(File from, File to) throws IOException {
        if(!to.exists()) {
            to.mkdirs();
        }
        for (File file : from.listFiles()) {
            if (file.isDirectory()) {
                copyFilesInDirectory(file, new File(to.getAbsolutePath() + "/" + file.getName()));
            } else {
                File n = new File(to.getAbsolutePath() + "/" + file.getName());
                Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}