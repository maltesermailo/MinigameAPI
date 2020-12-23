package de.maltesermailo.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.MinigameController;
import de.maltesermailo.api.minigame.AbstractCountdown;
import de.maltesermailo.api.minigame.AbstractMinigame;
import de.maltesermailo.api.minigame.Countdown;
import de.maltesermailo.api.minigame.GamePhaseManager;
import de.maltesermailo.api.minigame.GameRule;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.api.region.RegionManager;
import de.maltesermailo.impl.commands.CoinsCommand;
import de.maltesermailo.impl.commands.SpectatorCommand;
import de.maltesermailo.impl.minigame.CraftGamePhaseManager;
import de.maltesermailo.impl.minigame.gamerules.SharedGameRules;
import de.maltesermailo.impl.minigame.gamerules.block.BlockBreakGameRule;
import de.maltesermailo.impl.minigame.gamerules.block.BlockBurnGameRule;
import de.maltesermailo.impl.minigame.gamerules.block.BlockPlaceGameRule;
import de.maltesermailo.impl.minigame.gamerules.chat.JoinMessageGameRule;
import de.maltesermailo.impl.minigame.gamerules.chat.NoChatGameRule;
import de.maltesermailo.impl.minigame.gamerules.chat.QuitMessageGameRule;
import de.maltesermailo.impl.minigame.gamerules.entity.NoDamageGameRule;
import de.maltesermailo.impl.minigame.gamerules.entity.NoFoodLoseGameRule;
import de.maltesermailo.impl.minigame.gamerules.entity.NoMoveGameRule;
import de.maltesermailo.impl.minigame.gamerules.world.NoRainGameRule;
import de.maltesermailo.impl.minigame.gamerules.world.NoTimeChangeGameRule;
import de.maltesermailo.impl.minigame.gamerules.world.SetupModeGameRule;
import de.maltesermailo.impl.minigame.listener.AsyncPlayerChatListener;
import de.maltesermailo.impl.minigame.listener.AsyncPreLoginListener;
import de.maltesermailo.impl.minigame.listener.JoinListener;
import de.maltesermailo.impl.minigame.listener.SpectatorListener;
import de.maltesermailo.impl.minigame.region.CraftRegionManager;

public class CraftMinigameController implements MinigameController {

	private GamePhaseManager gamePhaseManager;
	
	private RegionManager regionManager;
	
	private FileConfiguration conf;
	
	private Location lobby;

	private Context ctx;

	private HikariDataSource database;

	private HashMap<String, GameRule> gameRules;
	private HashMap<Player, PlayerHandle> playerHandles;
	
	private int maxPlayers;
	
	public CraftMinigameController(Context ctx) {
		this.ctx = ctx;
		
		this.gamePhaseManager = new CraftGamePhaseManager(ctx);
		this.regionManager = new CraftRegionManager();
		
		this.gameRules = new HashMap<String, GameRule>();
		this.playerHandles = new HashMap<Player, PlayerHandle>();
		
		//Setup listener
		this.ctx.eventController().addListener(new JoinListener(this.ctx));
		this.ctx.eventController().addListener(new AsyncPreLoginListener());
		this.ctx.eventController().addListener(new AsyncPlayerChatListener());
		this.ctx.eventController().addListener(new SpectatorListener(this.ctx));
		
		//Setup commands
		Bukkit.getPluginCommand("coins").setExecutor(new CoinsCommand(this.ctx));
		Bukkit.getPluginCommand("spec").setExecutor(new SpectatorCommand(this.ctx));
		
		//Block Game Rules
		this.addGameRule(SharedGameRules.Block.BLOCK_BREAK.name(), new BlockBreakGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Block.BLOCK_PLACE.name(), new BlockPlaceGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Block.BLOCK_BURN.name(), new BlockBurnGameRule(this.ctx));
		
		//Entity Game Rules
		this.addGameRule(SharedGameRules.Entity.NO_DAMAGE.name(), new NoDamageGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Entity.NO_FOOD_LOSE.name(), new NoFoodLoseGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Entity.NO_MOVE.name(), new NoMoveGameRule(this.ctx));
		
		//Chat Game Rules
		this.addGameRule(SharedGameRules.Chat.JOIN_MESSAGE.name(), new JoinMessageGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Chat.QUIT_MESSAGE.name(), new QuitMessageGameRule(this.ctx));
		this.addGameRule(SharedGameRules.Chat.NO_CHAT.name(), new NoChatGameRule(this.ctx));
		
		//World Game Rules
		this.addGameRule(SharedGameRules.World.NO_RAIN.name(), new NoRainGameRule(this.ctx));
		this.addGameRule(SharedGameRules.World.NO_TIME_CHANGE.name(), new NoTimeChangeGameRule(this.ctx));
		this.addGameRule(SharedGameRules.World.SETUP_MODE.name(), new SetupModeGameRule(this.ctx));
		
		this.conf = this.ctx.getPlugin().getConfig();
		
		this.conf.addDefault("loc.lobby.x", 0);
		this.conf.addDefault("loc.lobby.y", 0);
		this.conf.addDefault("loc.lobby.z", 0);
		this.conf.addDefault("loc.lobby.yaw", 0);
		this.conf.addDefault("loc.lobby.pitch", 0);
		this.conf.addDefault("loc.lobby.world", Bukkit.getWorlds().get(0).getName());
		
		this.conf.options().copyDefaults(true);
		
		this.lobby = this.getLocation("lobby");
		
		this.ctx.getPlugin().saveConfig();
		
		if(MinigameAPI.instance().getConfig().getBoolean("mysql.enabled")) {
			this.mysql();
		}
	}
	
	@Override
	public void applyStandardGameRules() {
		this.getGameRule(SharedGameRules.Block.BLOCK_BREAK.name()).enable();
		this.getGameRule(SharedGameRules.Block.BLOCK_PLACE.name()).enable();
		this.getGameRule(SharedGameRules.Block.BLOCK_BURN.name()).enable();
		this.getGameRule(SharedGameRules.World.NO_RAIN).enable();
		this.getGameRule(SharedGameRules.World.NO_TIME_CHANGE).enable();
		this.getGameRule(SharedGameRules.Chat.JOIN_MESSAGE).enable();
		this.getGameRule(SharedGameRules.Chat.QUIT_MESSAGE).enable();
	}
	
	@Override
	public Location getLobbyLocation() {
		return this.lobby;
	}

	@Override
	public Location getLocation(String id) {
		return this.readLocation(id);
	}
	
	@Override
	public void setLocation(String id, Location l) {
		this.conf.set("loc." + id + ".x", l.getX());
		this.conf.set("loc." + id + ".y", l.getY());
		this.conf.set("loc." + id + ".z", l.getZ());
		this.conf.set("loc." + id + ".yaw", l.getYaw());
		this.conf.set("loc." + id + ".pitch", l.getPitch());
		this.conf.set("loc." + id + ".world", l.getWorld().getName());
		
		this.ctx.getPlugin().saveConfig();
	}

	private Location readLocation(String id) {
		if(this.conf.isConfigurationSection("loc." + id)) {
			ConfigurationSection section = this.conf.getConfigurationSection("loc." + id);
			
			double x = section.getDouble("x");
			double y = section.getDouble("y");
			double z = section.getDouble("z");
			
			float yaw = (float) section.getDouble("yaw");
			float pitch = (float) section.getDouble("pitch");
			
			String wName = section.getString("world");
			
			if(Bukkit.getWorld(wName) != null) {
				World w = Bukkit.getWorld(wName);
				
				return new Location(w, x, y, z, yaw, pitch);
			}
		}
		
		return null;
	}

	@Override
	public Countdown createCountdown(String id, int timeInSeconds, Callable<Void> callable) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void endGame() {
		//Send new status to cloud if plugin is enabled
		if(Bukkit.getPluginManager().isPluginEnabled("CloudSystem")) {
			this.ctx.cloudController().setStatus("finishing");
		}
		//Clear players inventory
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getActivePotionEffects().clear();
			
			PlayerHandle handle = this.getPlayerHandle(p);
			handle.setSpectator(false);
			
			if(handle.isNicked()) {
				handle.unnick();
			}
			
			p.teleport(this.lobby);
		}
		
		//Stopping gamePhaseManager
		this.getGamePhaseManager().setRunning(false);
		
		//Resetting game rules
		this.resetGameRules();
		this.getGameRule(SharedGameRules.Entity.NO_DAMAGE).enable();
		this.getGameRule(SharedGameRules.World.NO_RAIN).enable();
		this.getGameRule(SharedGameRules.World.NO_TIME_CHANGE).enable();
		
		Bukkit.broadcastMessage("§7Der Server startet in §e10 §7Sekunden neu.");
		
		//Shutdown countdown
		new AbstractCountdown(this.ctx, 10) {
			
			@Override
			public void onStop() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.kickPlayer("");
				}
				Bukkit.shutdown();
			}
			
			@Override
			public void onSecondTick() {
				if(this.getRemaining() <= 5)
					Bukkit.broadcastMessage("§7Der Server startet in §e" + this.getRemaining() + " §7Sekunden neu.");
				else if(this.getRemaining() <= 1) {
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
		            out.writeUTF("connectToLobby");
		            for(Player all : Bukkit.getOnlinePlayers()) {
		            	all.sendPluginMessage(ctx.getPlugin(), "nightcloud", out.toByteArray());
		    		}
				}
			}
		};
	}

	@Override
	public boolean forceStart() {
		AbstractMinigame miniGame = (AbstractMinigame)this.ctx.getPlugin();
		
		if(miniGame.getLobbyTimer().getRemaining() > 11) {
			miniGame.getLobbyTimer().setRemaining(11);
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void startLobbyTimer() {
		AbstractMinigame miniGame = (AbstractMinigame)this.ctx.getPlugin();
		
		if(miniGame.getLobbyTimer() == null) {
			miniGame.startLobbyTimer();
		}
	}

	@Override
	public void addGameRule(String name, GameRule rule) {
		this.gameRules.put(name, rule);
	}
	
	public GameRule getGameRule(String name) {
		return this.gameRules.get(name);
	}
	
	@Override
	public void resetGameRules() {
		for(GameRule g : this.gameRules.values()) {
			g.disable();
		}
		
		this.applyStandardGameRules();
	}
	
	@Override
	public GameRule getGameRule(Enum<?> enumGameRule) {
		return this.getGameRule(enumGameRule.name());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, GameRule> getGameRules() {
		return (HashMap<String, GameRule>) this.gameRules.clone();
	}

	@Override
	public GamePhaseManager getGamePhaseManager() {
		return this.gamePhaseManager;
	}
	
	@Override
	public RegionManager getRegionManager() {
		return this.regionManager;
	}

	@Override
	public HikariDataSource mysql() {
		if(this.database != null) {
			return this.database;
		}
		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + MinigameAPI.instance().getConfig().getString("mysql.host") + ":3306/" + MinigameAPI.instance().getConfig().getString("mysql.database"));
		config.setMaximumPoolSize(5);
		config.setUsername(MinigameAPI.instance().getConfig().getString("mysql.user"));
		config.setPassword(MinigameAPI.instance().getConfig().getString("mysql.password"));
		
		this.database = new HikariDataSource(config);
		
		Bukkit.getLogger().info("[LegendaryAPI] [Minigame] Connection to mysql is successful.");
		
		return this.database;
	}

	@Override
	public PlayerHandle getPlayerHandle(Player p) {
		return this.playerHandles.get(p);
	}
	
	@Override
	public Collection<PlayerHandle> getPlayerHandles() {
		return this.playerHandles.values();
	}
	
	@Override
	public PlayerHandle createHandle(Player p) {
		PlayerHandle playerHandle = new CraftPlayerHandle(this.ctx ,p);
		
		this.playerHandles.put(p, playerHandle);
		
		return playerHandle;
	}
	
	void destroyHandle(PlayerHandle playerHandle, String customReason) {
		this.playerHandles.remove(playerHandle.getPlayer());
		
		if(playerHandle.getPlayer().isOnline()) {
			playerHandle.getPlayer().kickPlayer(customReason);
		}
	}

	@Override
	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	@Override
	public void setMaxPlayers(int i) {
		this.maxPlayers = i;
	}

}
