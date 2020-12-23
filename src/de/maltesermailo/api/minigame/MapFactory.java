package de.maltesermailo.api.minigame;

import java.util.List;

public interface MapFactory {

	/**
	 * Returns the current map
	 * @return
	 */
	public Map current();
	
	/**
	 * Sets the current map
	 * @param map
	 */
	public void setCurrentMap(Map map);
	
	/**
	 * Sets map class type for saving .map file into Map class
	 * @param mapClass
	 */
	void setMapClassType(Class<? extends Map> mapClass);
	
	/**
	 * Sets map directory where the worlds and the .map files are searched in %WORLD_DIR%/*.map
	 * @param dir
	 */
	public void setMapDirectory(String dir);

	/**
	 * @return mapDirectory
	 */
	public String getMapDirectory();
	
	/**
	 * Returns a list of all available maps
	 * @return List<Map>
	 */
	public List<Map> getMaps();
	
	/**
	 * Returns random map from the map pool. <b>Map Pool must loaded before by calling loadMaps()!</b>
	 * @return Map
	 */
	public Map getRandomMap();
	
	/**
	 * Returns map with the given name
	 * @param mapName
	 * @return map
	 */
	public Map getMap(String mapName);
	
	/**
	 * Loads map into the server, so that players can teleported
	 * @param map
	 * @return
	 */
	public boolean loadMap(Map map);
	
	/**
	 * Copies files into world container
	 * @param map
	 * @return boolean
	 */
	public boolean copyWorld(Map map);
	
	/**
	 * Loads all maps into the map pool
	 */
	public void loadMaps();
	
	/**
	 * Saves map file into map Directory with the world name
	 * @param map
	 */
	public void saveMap(Map map);

	/**
	 * Returns whether there is at least one map existing
	 * @return boolean
	 */
    boolean mapsExisting();
}
