package de.elwiron.bedwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.gamemodes.LobbyTime;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class Start implements CommandExecutor {

    private BedWars plugin;

    public Start(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("start")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(BedWars.getInstance().getPrefix()  +"§cNur player Dürfen /start nutzen");
                return true;
            }
            Player p = (Player) sender;
            if(BedWars.getInstance().getGameOwner() != null) {
    			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.YOUTUBER) || BedWars.getInstance().getGameOwner().equals(p.getUniqueId()))) {
    				p.sendMessage(BedWars.getInstance().getNoperm());
    				return true;
    			} 
            }else {
    			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.YOUTUBER))) {
    				p.sendMessage(BedWars.getInstance().getNoperm());
    				return true;
    			} 
            }
    		

            if(LobbyTime.OBJ.getContdown() <= 12){
                p.sendMessage(BedWars.getInstance().getPrefix() +"§cDas Spiel wurde schon gestartet");
                return true;
            }

            LobbyTime.OBJ.setContdown(11);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(BedWars.getInstance().getPrefix() + "§aDas Spiel wurde gestartet");
            }

        }
        return true;
    }
}

