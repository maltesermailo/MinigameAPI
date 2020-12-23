package de.maltesermailo.api.minigame;

import org.bukkit.Location;

/**
 * Class representing a map
 * @author Jonas
 *
 */
public interface Map {

	public String getMapName();
	
	public String getAuthor();
	
	public Location getSpectatorSpawn();
	
	public String getPath();
	
	public int getMaxPlayers();
	
}
