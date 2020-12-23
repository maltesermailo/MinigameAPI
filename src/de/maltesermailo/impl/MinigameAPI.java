package de.maltesermailo.impl;

import java.util.HashSet;
import java.util.Set;

import de.maltesermailo.impl.minigame.region.CraftRegionManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import de.maltesermailo.api.Context;
import de.maltesermailo.impl.nick.NickHandler;
import de.maltesermailo.impl.redis.JedisController;

public class MinigameAPI extends JavaPlugin {
	
	private static MinigameAPI instance;
	private static JedisController jedis;
	private static NickHandler nickHandler;
	private static CraftRegionManager regionManager;
	
	private HikariDataSource db;
	
	public MinigameAPI() {
		MinigameAPI.instance = this;
	}
	
	private Set<Context> contexts = new HashSet<>();

	public void onEnable() {
		this.getConfig().addDefault("lobbyMode", false);
		this.getConfig().addDefault("messages.nick.nick", "&5Nick &8| &5Du bist nun als &a%NAME% &5genickt.");
		this.getConfig().addDefault("messages.nick.unnick", "&5Nick &8| &eDein Nick wurde deaktiviert.");
		this.getConfig().addDefault("messages.nick.prefix", "§a");
		
		this.getConfig().addDefault("mysql.enabled", false);
		this.getConfig().addDefault("mysql.host", "127.0.0.1");
		this.getConfig().addDefault("mysql.port", 3306);
		this.getConfig().addDefault("mysql.user", "root");
		this.getConfig().addDefault("mysql.password", "root");
		this.getConfig().addDefault("mysql.database", "database");
		this.getConfig().addDefault("mysql.poolsize.nick", 5);
		this.getConfig().addDefault("mysql.poolsize.minigame", 5);
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		MinigameAPI.jedis = new JedisController();
		MinigameAPI.nickHandler = new NickHandler();
		MinigameAPI.regionManager = new CraftRegionManager();
		
		if(this.getConfig().getBoolean("mysql.enabled")) {
			this.mysql();
		}
	}
	
	public static MinigameAPI instance() {
		return MinigameAPI.instance;
	}
	
	public static Set<Context> getContexts() {
		return instance().contexts;
	}
	
	public PluginContext createContext(Plugin plugin) {
		PluginContext ctx = new PluginContext(plugin);
		this.contexts.add(ctx);
		return ctx;
	}
	
	public static JedisController getJedisController() {
		return jedis;
	}

	public static NickHandler getNickHandler() {
		return nickHandler;
	}

	public static CraftRegionManager getRegionManager() {
		return regionManager;
	}
	
	public HikariDataSource mysql() {
		if(this.db != null) {
			return this.db;
		}
		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + this.getConfig().getString("mysql.host") + ":3306/" + this.getConfig().getString("mysql.database"));
		config.setMaximumPoolSize(this.getConfig().getInt("mysql.user"));
		config.setUsername(this.getConfig().getString("mysql.user"));
		config.setPassword(this.getConfig().getString("mysql.password"));
		
		this.db = new HikariDataSource(config);
		
		Bukkit.getLogger().info("[LegendaryAPI] Initialized MySQL Connection and MySQL Pools.");
		
		return this.db;
	}
	
}
