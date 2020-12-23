package de.maltesermailo.impl;

import de.maltesermailo.impl.minigame.CraftMapFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.maltesermailo.api.BlockController;
import de.maltesermailo.api.CloudController;
import de.maltesermailo.api.CommandController;
import de.maltesermailo.api.Context;
import de.maltesermailo.api.EffectController;
import de.maltesermailo.api.EntityController;
import de.maltesermailo.api.EventController;
import de.maltesermailo.api.MinigameController;

public class PluginContext implements Context {

	private BlockController blockController;
	
	private CommandController commandController;
	
	private EffectController effectController;
	
	private EntityController entityController;
	
	private EventController eventController;
	
	private MinigameController minigameController;
	
	private CloudController cloudController;

	private CraftMapFactory mapFactory;
	
	private Plugin plugin;
	
	public PluginContext(Plugin pl) {
		this.plugin = pl;
		
		this.eventController = new CraftEventController(this);
		this.effectController = new CraftEffectController();
		this.commandController = new CraftCommandController(this);
		this.minigameController = new CraftMinigameController(this);
		this.mapFactory = new CraftMapFactory(this);
		if(Bukkit.getPluginManager().isPluginEnabled("CloudSystem")) {
			this.cloudController = new CraftCloudController(this);
		}
		
		new CraftPluginManager();
	}
	
	@Override
	public BlockController blockController() {
		return this.blockController;
	}

	@Override
	public CommandController commandController() {
		return this.commandController;
	}

	@Override
	public EffectController effectController() {
		return this.effectController;
	}

	@Override
	public EntityController entityController() {
		return this.entityController;
	}

	@Override
	public EventController eventController() {
		return this.eventController;
	}

	@Override
	public MinigameController miniGameController() {
		return this.minigameController;
	}

	public CloudController cloudController() {
		return this.cloudController;
	}

	public CraftMapFactory mapFactory() {
		return mapFactory;
	}
	
	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

}
