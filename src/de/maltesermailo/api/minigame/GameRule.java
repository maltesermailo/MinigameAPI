package de.maltesermailo.api.minigame;

public interface GameRule {
	
	/**
	 * Enables the game rule
	 */
	public void enable();
	
	/**
	 * Disables the game rule
	 */
	public void disable();
	
	/**
	 * Checks if the GameRule is enabled
	 * @return
	 */
	public boolean isEnabled();
	
	/**
	 * Registers the events in the GameRule class
	 */
	public void registerEvents();

}
