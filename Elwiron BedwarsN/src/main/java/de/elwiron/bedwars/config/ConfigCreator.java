package de.elwiron.bedwars.config;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConfigCreator {

	public ConfigManager manager;
	public static ConfigHandler cfg;
	public static ConfigHandler location;
	public static ConfigHandler spawner;
	public static ConfigHandler shop;

	public void createConfig() {
		this.manager = new ConfigManager();
		ConfigCreator.cfg = this.manager.getNewConfig("config");
		if (ConfigCreator.cfg.isFirstrun()) {

			ConfigCreator.cfg.set("BedWars.Lobby.Time", 60);
			ConfigCreator.cfg.set("BedWars.MinPlayers", 2);
			ConfigCreator.cfg.set("BedWars.Team.Teams", 4);
			ConfigCreator.cfg.set("BedWars.Team.Players", 4);
			ConfigCreator.cfg.saveConfig();
		}
	}

	public void createLocation() {
		this.manager = new ConfigManager();
		ConfigCreator.location = this.manager.getNewConfig("location");
		if (ConfigCreator.location.isFirstrun()) {

			
			ConfigCreator.location.set("BedWars.Spawn.Loction", new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
			ConfigCreator.location.set("BedWars.Maps", new ArrayList<String>());

			ConfigCreator.location.saveConfig();
		}
	}

	public void createSpawner() {
		this.manager = new ConfigManager();
		ConfigCreator.spawner = this.manager.getNewConfig("spawner");
		if (ConfigCreator.spawner.isFirstrun()) {
			ConfigCreator.spawner.saveConfig();
		}
	}

	public void createShop() {
		this.manager = new ConfigManager();
		ConfigCreator.shop = this.manager.getNewConfig("shop");
		if (ConfigCreator.shop.isFirstrun()) {
			ConfigCreator.shop.saveConfig();
		}
	}

}
