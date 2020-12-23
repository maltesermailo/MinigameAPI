package de.maltesermailo.api.minigame;

public interface GamePhaseManager {

	/**
	 * Registers a new gamephase by the given name
	 * @param name the name
	 * @param g the gamephase
	 */
	public void registerGamePhase(String name, GamePhase g);
	
	/**
	 * @return the current game phase
	 */
	public GamePhase current();
	
	/**
	 * @param name the game phase name
	 * @return the game phase by name
	 */
	public GamePhase getGamePhase(String name);
	
	/**
	 * Calls a game phase directly and stops the current if there is one
	 * @param g the gamephase
	 */
	public void callGamePhase(GamePhase g);
	
	/**
	 * Sets the running state of the GamePhaseManager(true for running, false for editing)
	 * Also sets current to null
	 * @param run the boolean
	 */
	public void setRunning(boolean run);

	/**
	 * Tests if the running state of the GamePhaseManager is run
	 * @return the running state
	 */
	public boolean isRunning();
	
}
