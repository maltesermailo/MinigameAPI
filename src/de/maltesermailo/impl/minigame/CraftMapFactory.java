package de.maltesermailo.impl.minigame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.Map;
import de.maltesermailo.api.minigame.MapFactory;
import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.PluginContext;
import net.md_5.bungee.api.ChatColor;

public class CraftMapFactory implements MapFactory {

	private Context ctx;
	
	private Map current = null;
	private Class<? extends Map> mapClass;
	private String mapDir;
	private List<Map> mapPool = new ArrayList<Map>();
	
	public CraftMapFactory(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public Map current() {
		return this.current;
	}

	@Override
	public void setCurrentMap(Map map) {
		this.current = map;
		this.ctx.miniGameController().setMaxPlayers(map.getMaxPlayers());
	}
	
	@Override
	public void setMapClassType(Class<? extends Map> mapClass) {
		this.mapClass = mapClass;
	}

	@Override
	public void setMapDirectory(String dir) {
		File file = new File(dir);
		if(file.exists()) {
			this.mapDir = dir;
		} else {
			this.mapDir = MinigameAPI.instance().getDataFolder().getAbsolutePath() + "/../../maps/";
		}
	}

	@Override
	public String getMapDirectory() {
		return this.mapDir;
	}

	@Override
	public List<Map> getMaps() {
		return this.mapPool;
	}
	
	@Override
	public Map getRandomMap() {
		Random random = new Random();
		int randomMap = random.nextInt(mapPool.size());
		
		Map map = mapPool.get(randomMap);
		
		return map;
	}

	@Override
	public Map getMap(String mapName) {
		for(Map map : mapPool) {
			if(map.getMapName().equalsIgnoreCase(mapName)) {
				return map;
			}
		}
		return null;
	}

	@Override
	public boolean loadMap(Map map) {
		int length = map.getPath().split(File.separator).length;
		File mapDir = new File(Bukkit.getWorldContainer().getAbsolutePath(), map.getPath().split(File.separator)[length-1]);
		if(!mapDir.exists() 
				&& !this.copyWorld(map)) {
			Bukkit.getLogger().severe("[MinigameAPI] [MapLoader] Map couldn't loaded from map directory, map couldn't copied. \n"
					+ "Current Time: " + new Date(System.currentTimeMillis()).toString()
					+ "\nCurrent Map: " + map.getMapName()
					+ "\nMap Path: " + map.getPath());
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cEs trat ein Server Fehler auf, bitte kontaktiert einen Administrator mit folgenden Code: " + System.currentTimeMillis()));
			this.ctx.miniGameController().endGame();
			return false;
		}
		
		WorldCreator wc = new WorldCreator(map.getMapName());
		wc.environment(World.Environment.NORMAL);
		wc.generateStructures(false);
		wc.type(WorldType.FLAT);
		
		World world = Bukkit.getServer().createWorld(wc);
		world.setDifficulty(Difficulty.EASY);
		world.setAnimalSpawnLimit(0);
		world.setMonsterSpawnLimit(0);
		world.setAutoSave(false);
		return true;
	}

	@Override
	public void loadMaps() {
		if(this.mapDir == null) {
			Bukkit.getLogger().severe("[LegendaryAPI] Loading Maps failed; No Map directory set");
			return;
		}
		
		Gson gson = new GsonBuilder().create();
		
		for(File dir : new File(this.mapDir).listFiles()) {
			if(dir.isDirectory()) {
				for(File file : dir.listFiles()) {
					if(file.getName().endsWith(".map")) {
						try {
							
							this.mapPool.add(gson.fromJson(new FileReader(file), this.mapClass));
							Bukkit.getLogger().info("[LegendaryAPI] Added map to map pool");
						} catch (JsonSyntaxException | JsonIOException
								| FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	@Override
	public void saveMap(Map map) {
		if(map.getPath() == null) {
			Bukkit.getLogger().severe("[LegendaryAPI] Couldn't save map, path is not given");
		}
		
		String path = map.getPath();
		File mapFile = new File(path, map.getMapName() + ".map");
		
		if(!mapFile.exists()) {
			try {
				mapFile.createNewFile();
				
				Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.registerTypeAdapter(Location.class, new LocationJSONSerializer())
						.create();
				
				FileWriter fw = new FileWriter(mapFile);
				
				fw.write(gson.toJson(map));
				fw.flush();
				
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean mapsExisting() {
		if(this.mapPool.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean copyWorld(Map map) {
		File mapDir = new File(map.getPath());
		
		if(mapDir.exists()) {
			Bukkit.getLogger().info("Copying map from [" + map.getPath() + "] to [" + Bukkit.getWorldContainer() + "]");
			int length = map.getPath().split(File.separator).length;
			try {
				FileUtils.copyDirectory(mapDir, new File(Bukkit.getWorldContainer().getAbsolutePath(), map.getPath().split(File.separator)[length-1]));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
