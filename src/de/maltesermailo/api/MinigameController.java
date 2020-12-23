package de.maltesermailo.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;
import com.zaxxer.hikari.HikariDataSource;

import de.maltesermailo.api.minigame.Countdown;
import de.maltesermailo.api.minigame.GamePhaseManager;
import de.maltesermailo.api.minigame.GameRule;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.api.region.RegionManager;

public interface MinigameController {
	
	/**
	 * Gets the lobby location from the minigame configuration
	 * @return the lobby location
	 */
	public Location getLobbyLocation();
	
	/**
	 * Gets a spawn from the minigame configuration
	 * @param id the id
	 * @return the location
	 */
	public Location getLocation(String id);
	
	/**
	 * Sets a location in the minigame configuration
	 * @param id the id
	 * @param l the location
	 */
	public void setLocation(String id, Location l);
	
	/**
	 * Not implemented yet.
	 * Creates a new countdown
	 * @param id the countdown id
	 * @param timeInSeconds the time in seconds
	 * @return the countdown
	 */
	@Deprecated
	public Countdown createCountdown(String id, int timeInSeconds, Callable<Void> callable);
	
	/**
	 * Ends the game
	 */
	public void endGame();
	
	/**
	 * Force starts the game
	 * @return 
	 */
	public boolean forceStart();
	
	/**
	 * Starts the lobby timer
	 * @return 
	 */
	public void startLobbyTimer();
	
	/**
	 * Adds a global game rule
	 * @param name the name
	 * @param rule the gamerule
	 */
	public void addGameRule(String name, GameRule rule);
	
	/**
	 * Gets the Game rules
	 * @return the game rules as list
	 */
	public HashMap<String, GameRule> getGameRules();
	
	/**
	 * Gets the Game Rule by name
	 * @param name the name
	 * @return the game rule if found
	 */
	public GameRule getGameRule(String name);
	
	/**
	 * Gets the Game Rule by name
	 * @param enum the game rule enum
	 * @return the game rule if found
	 */
	public GameRule getGameRule(Enum<?> enumGameRule);
	
	/**
	 * Disables all game rules.
	 * And applies the standard game rules.
	 */
	public void resetGameRules();
	
	/**
	 * Applies standard game rules
	 */
	public void applyStandardGameRules();
	
	/**
	 * Gets the current Game Phase Manager
	 * @return the GamePhaseManager
	 */
	public GamePhaseManager getGamePhaseManager();
	
	/**
	 * Gets the region Manager for managing regions
	 * @return the RegionManager
	 */
	public RegionManager getRegionManager();
	
	/**
	 * Gets the current mysql ebean instance
	 * @return the ebeanserver
	 */
	public HikariDataSource mysql();
	
	/**
	 * Gets a player handle
	 * @return the player handle or null if it not exists
	 */
	public PlayerHandle getPlayerHandle(Player p);
	
	/**
	 * Registers a new player and returns the handle
	 * @return the new player handle or the existing if one exists
	 */
	public PlayerHandle createHandle(Player p);
	
	/**
	 * Returns all Player Handles
	 */
	public Collection<PlayerHandle> getPlayerHandles();
	
	public int getMaxPlayers();
	
	public void setMaxPlayers(int i);

}
