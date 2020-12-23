package de.maltesermailo.api.minigame;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

public interface PlayerHandle {

	/**
	 * Gets Current scoreboard
	 * @return the scoreboard
	 */
	public PlayerScoreboard getCurrentScoreboard();
	
	/**
	 * Player object
	 * @return Player
	 */
	public Player getPlayer();
	
	/**
	 * PlayerData object
	 * @return player data instance
	 */
	public <T> T getPlayerData();
	
	/**
	 * Sets player data instance of player
	 * @param data
	 */
	public void setPlayerData(PlayerData data);
	
	/**
	 * MySQL UUID
	 * @return
	 */
	public String getUUID();
	
	
	/**
	 * Gets if the player is spectating
	 * @return
	 */
	public boolean isSpectator();
	
	/**
	 * Set current scoreboard
	 * @param scoreboard
	 */
	public void setCurrentScoreboard(PlayerScoreboard scoreboard);
	
	/**
	 * Set this player to spectator mode
	 * @param isSpectator boolean
	 */
	public void setSpectator(boolean isSpectator);
	
	/**
	 * Set points
	 * @param points the points
	 */
	public void setCoins(int coins);
	
	/**
	 * Get points
	 * @return the points
	 */
	public int getCoins();
	
	/**
	 * Nicks the player
	 */
	public void nick(String nick);
	
	/**
	 * Unnicks the player
	 */
	public void unnick();
	
	/**
	 * Checks if player is nicked
	 * @return true if player is nicked, false if not
	 */
	public boolean isNicked();
	
	/**
	 * Refreshs the player's nick
	 */
	public void refreshNick();
	
	/**
	 * Destroys this player handle removing it from the server.
	 */
	public void destroy();
	
	/**
	 * Destroys this player handle with a reason
	 * @param reason
	 */
	public void destroy(String reason);
	
	/**
	 * Reloads player data from database
	 */
	public void reload();
	
	/**
	 * Saves player data to database
	 */
	public void save();
	
	/**
	 * Waits until player is loaded from database
	 * @param Consumer<PlayerHandle>
	 */
	public void waitForLoading(Consumer<PlayerHandle> callback);
}
