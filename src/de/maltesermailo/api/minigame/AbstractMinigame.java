package de.maltesermailo.api.minigame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.maltesermailo.api.Context;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;

public abstract class AbstractMinigame extends JavaPlugin {

	private Context context;
	
	private Countdown lobbyTimer;
	
	protected static AbstractMinigame instance;
	
	public Context context() {
		return this.context;
	}
	
	public Countdown getLobbyTimer() {
		return this.lobbyTimer;
	}
	
	public Countdown startLobbyTimer() {
		this.lobbyTimer = new AbstractCountdown(this.context(), 60) {
			
			@Override
			public void onStop() {
				if(Bukkit.getOnlinePlayers().size() < AbstractMinigame.this.getConfig().getInt("minPlayers")) {
					lobbyTimer = null;
					return;
				}
			}
			
			@Override
			public void onSecondTick() {
				Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(this.getRemaining()));
				AbstractMinigame.this.onSecondTick(getRemaining());
			}
		};
		
		return this.lobbyTimer;
	}
	
	@Override
	public void onLoad() {
		load();
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.context = Context.createContext(this);
		
		this.getConfig().addDefault("minPlayers", 2);
		this.getConfig().addDefault("joinFullMessage", "§cServer is full.");
		this.getConfig().addDefault("premiumKickedMessage", "§cYou were kicked by an premium.");
		this.getConfig().addDefault("premiumPermission", "permissions.premium");
		
		this.getConfig().options().copyDefaults(true);
		
		this.saveConfig();
		
		enable();
	}
	
	public LuckPermsApi getLuckPermsApi() {
		return LuckPerms.getApi();
	}
	
	public abstract void onSecondTick(int remaining);
	public abstract void load();
	public abstract void enable();
	public abstract void onMapChange(Map map);
	
}
