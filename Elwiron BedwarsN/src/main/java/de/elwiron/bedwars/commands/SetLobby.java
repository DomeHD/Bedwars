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

public class SetLobby implements CommandExecutor {

	private BedWars plugin;

	public SetLobby(BedWars Instance) {
		this.plugin = BedWars.getInstance();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("setlobby")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(BedWars.getInstance().getPrefix() + "§cNur Spieler dürfen /Setlobby nutzen");
				return true;
			}

			if(BedWars.getInstance().getState() != GameState.MAINTENANCE){
				sender.sendMessage(BedWars.getInstance().getPrefix() + "§cDas kannst du nur im Wartungsmodus machen!");
				return true;
			}
			Player p = (Player) sender;
			
			if (!(PlayerAPI.getPlayerTypes().get(p.getUniqueId()).getRang().isHigerOrEquals(Rang.ADMIN))) {
				p.sendMessage(BedWars.getInstance().getNoperm());
				return true;
			}

			ConfigCreator.location.set("BedWars.Spawn.Loction", p.getLocation());
			ConfigCreator.location.saveConfig();
			p.sendMessage("§aLobby gestezt!");
			

			
		}
		return true;
	}

}
