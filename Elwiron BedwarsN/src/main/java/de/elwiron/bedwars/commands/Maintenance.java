package de.elwiron.bedwars.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elwiron.bedwars.BedWars;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.utils.Rang;

public class Maintenance implements CommandExecutor {

    private BedWars plugin;

    public Maintenance(BedWars Instance) {
        this.plugin = BedWars.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("maintenance")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cNur Spieler dürfen /Maintenance nutzen");
                return true;
            }

            Player p = (Player) sender;

			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN))) {
				p.sendMessage(BedWars.getInstance().getNoperm());
				return true;
			}
            
                for(Player all : BedWars.getInstance().getServer().getOnlinePlayers()){
                    if(!all.getName().equals(p.getName())){
                        all.kickPlayer(plugin.getPrefix() + "§cDu wurdes gekickt damit §a" + p.getName() + " §cDen Server Warten kann");
                    }

                }

                BedWars.getInstance().setState(GameState.MAINTENANCE);
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(plugin.getPrefix() + "§aDer Server ist Jetzt im §eWartungsmodus!");
                p.sendMessage(plugin.getPrefix() + "§aServer Restart um den Wartungsmodus zu bennden!");
            }

        
        return true;
    }

}
