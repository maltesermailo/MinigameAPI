package de.maltesermailo.api.minigame;

import org.bukkit.Location;

public abstract class AbstractMap implements Map {

	private String name;
	private String author;
	private Location spectatorSpawn;
	private String path;
	private int maxplayers;
	
	public AbstractMap(String name, String author, Location spectatorSpawn, String worldPath) {
		this.name = name;
		this.author = author;
		this.spectatorSpawn = spectatorSpawn;
		this.path = worldPath;
	}
	
	@Override
	public String getMapName() {
		return this.name;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public Location getSpectatorSpawn() {
		return this.spectatorSpawn;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public int getMaxPlayers() {
		return this.maxplayers;
	}

}
