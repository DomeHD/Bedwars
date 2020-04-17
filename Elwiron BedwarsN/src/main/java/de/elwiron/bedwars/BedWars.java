package de.elwiron.bedwars;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.api.player.PlayerExecutorBridge;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;
import de.dytanic.cloudnet.lib.utility.document.Document;
import de.elwiron.bedwars.commands.CreateMap;
import de.elwiron.bedwars.commands.LoadWorld;
import de.elwiron.bedwars.commands.Maintenance;
import de.elwiron.bedwars.commands.SetBed;
import de.elwiron.bedwars.commands.SetLobby;
import de.elwiron.bedwars.commands.SetLocation;
import de.elwiron.bedwars.commands.SetShop;
import de.elwiron.bedwars.commands.SetSpawner;
import de.elwiron.bedwars.commands.Start;
import de.elwiron.bedwars.commands.WorldTP;
import de.elwiron.bedwars.config.ConfigCreator;
import de.elwiron.bedwars.listener.BlockBreak;
import de.elwiron.bedwars.listener.BlockExplode;
import de.elwiron.bedwars.listener.BlockPlace;
import de.elwiron.bedwars.listener.ChunkUnloadListener;
import de.elwiron.bedwars.listener.CraftItem;
import de.elwiron.bedwars.listener.EntityDamage;
import de.elwiron.bedwars.listener.EntityDamageByEntity;
import de.elwiron.bedwars.listener.InventoryClick;
import de.elwiron.bedwars.listener.PlayerChat;
import de.elwiron.bedwars.listener.PlayerDeath;
import de.elwiron.bedwars.listener.PlayerDropItem;
import de.elwiron.bedwars.listener.PlayerInteract;
import de.elwiron.bedwars.listener.PlayerInteractEntity;
import de.elwiron.bedwars.listener.Join;
import de.elwiron.bedwars.listener.PlayerArmorStandManipulate;
import de.elwiron.bedwars.listener.PlayerMove;
import de.elwiron.bedwars.listener.PlayerQuit;
import de.elwiron.bedwars.listener.PlayerRespawn;
import de.elwiron.bedwars.listener.WeatherChange;
import de.elwiron.bedwars.utils.GameState;
import de.elwiron.bedwars.utils.Teams;
import de.elwiron.mangemant.bungee.mysql.Mysql;
import de.elwiron.mangemant.spigot.api.PlayerAPI;
import de.elwiron.mangemant.spigot.events.PlayerJoin;
import de.elwiron.mangemant.utils.GameType;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class BedWars extends JavaPlugin {

    @Getter
    private String prefix = "§b[Rush-Bedwars] §7";
    @Getter
    private String noperm = prefix + "§cDafür hast du nicht die nötigen Rechten!";
    @Getter
    private String mapName = "Bedwars4x4-2";
    @Getter
    private int maxTeams = 4;
    @Getter
    private  int maxPlayersTeam = 4;

    @Getter
    private static BedWars instance;
    @Getter
    private Scoreboard sb;
    @Getter
    ConfigCreator configCreator;
    @Getter
    @Setter
    GameState state = GameState.STARTING;
    @Getter
    private static int maxPlayers;
    @Getter
    private ArrayList<Teams> aktivTeams;

    @Getter
    UUID GameOwner = null;
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        sb = Bukkit.getScoreboardManager().getNewScoreboard();


        configCreator = new ConfigCreator();
        configCreator.createConfig();
        configCreator.createLocation();
        configCreator.createSpawner();
        configCreator.createShop();


        maxTeams = ConfigCreator.cfg.getInt("BedWars.Team.Teams");
        maxPlayersTeam = ConfigCreator.cfg.getInt("BedWars.Team.Players");
        maxPlayers = maxPlayersTeam * maxTeams;


        registerTeams();



        registerCommand();
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aplugin gestartet!");
        state = GameState.LOBBY;
        loadmaps();
        
        CloudServer.getInstance().setMotd("§2Map§f: §b" + mapName);
        CloudServer.getInstance().setMaxPlayers(maxPlayers);
        CloudServer.getInstance().update();
        CloudServer.getInstance().setServerState(ServerState.LOBBY); 
        
        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorlds().get(0).setAmbientSpawnLimit(0);
        Bukkit.getWorlds().get(0).setMonsterSpawnLimit(0);
        PlayerAPI.setDefaultChat(false);
        PlayerAPI.setDefaultJoinMessage(true);
        PlayerAPI.setGameType(GameType.BEDWARS);

        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorlds().get(0).setAmbientSpawnLimit(0);
        Bukkit.getWorlds().get(0).setMonsterSpawnLimit(0);
        
        if(maxPlayers == 2) {
            Document properties = CloudServer.getInstance().getServerConfig().getProperties();
            if (properties.contains("pp2")) {
            	PlayerExecutorBridge.INSTANCE.sendPlayer(CloudAPI.getInstance().getOnlinePlayer(properties.getObject("pp2", UUID.class)), CloudAPI.getInstance().getServerId());
            }
            if (properties.contains("pp1")) {
            	PlayerExecutorBridge.INSTANCE.sendPlayer(CloudAPI.getInstance().getOnlinePlayer(properties.getObject("pp1", UUID.class)), CloudAPI.getInstance().getServerId());
            	GameOwner = CloudAPI.getInstance().getOnlinePlayer(properties.getObject("pp1", UUID.class)).getUniqueId();
            }

    }
        
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Mysql.disconnect();
        Bukkit.getConsoleSender().sendMessage(prefix + "§cplugin gestopt!");
    }

    private void registerCommand(){
        getCommand("maintenance").setExecutor(new Maintenance(this));
        getCommand("loadWorld").setExecutor(new LoadWorld(this));
        getCommand("createmap").setExecutor(new CreateMap(this));
        getCommand("worldtp").setExecutor(new WorldTP(this));
        getCommand("setlocation").setExecutor(new SetLocation(this));
        getCommand("setlobby").setExecutor(new SetLobby(this));
        getCommand("start").setExecutor(new Start(this));
        getCommand("setspawner").setExecutor(new SetSpawner(this));
        getCommand("setshop").setExecutor(new SetShop(this));
        getCommand("setbed").setExecutor(new SetBed(this));

    }

    private void registerEvents(){
    	new PlayerRespawn(instance);
       // new ServerListPing(instance);
        //new PlayerLogin(instance);
        new Join(instance);
        new PlayerQuit(instance);
        new BlockBreak(instance);
        new BlockPlace(instance);
        new EntityDamage(instance);
        new InventoryClick(instance);
        new PlayerDropItem(instance);
        new PlayerMove(instance);
        new PlayerInteract(instance);
        new EntityDamageByEntity(instance);
        new PlayerDeath(instance);
        new ChunkUnloadListener(instance);
        new PlayerInteractEntity(instance);
        new PlayerChat(instance);
        new PlayerArmorStandManipulate(instance);
        new WeatherChange(instance);
        new BlockExplode(instance);
        new CraftItem(instance);
    }

    private void registerTeams(){
        int i = 0;
        aktivTeams = new ArrayList<Teams>();
        for (Teams t : Teams.values()) {
            if(i >= maxTeams){
                break;
            }
            PlayerJoin.sb.registerNewTeam(t.getDisplayName()).setPrefix(t.getPrefix());
            aktivTeams.add(t);

            i++;
        }
        PlayerJoin.sb.registerNewTeam(Teams.SPECTATOR.getName()).setPrefix(Teams.SPECTATOR.getPrefix());
        PlayerJoin.sb.registerNewTeam(Teams.NOTEAM.getName()).setPrefix(Teams.NOTEAM.getPrefix());
    }

	private void loadmaps() {
		int i = 0;
		ArrayList<String> map = (ArrayList) ConfigCreator.location.get("Game.maps");
		ArrayList<String> isMap = new ArrayList<String>();
		
		for(String str : map){
			if(ConfigCreator.location.getInt("Game.map." + str + ".teams") == maxTeams){
				isMap.add(str);
			}
		}
		
		
		Random rn = new Random();
		int r1;
		r1 = rn.nextInt((isMap.size() - 1) + 1) + 1;

			
		 ArrayList<String> eMaps = new ArrayList<String>();
		 mapName = map.get(r1 - 1);
		 InventoryClick.setMaps(eMaps);
	}



}

