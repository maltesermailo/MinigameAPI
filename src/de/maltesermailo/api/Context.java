package de.maltesermailo.api;

import de.maltesermailo.api.minigame.MapFactory;
import org.bukkit.plugin.Plugin;

import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.api.BlockController;

/**
 * Basic Context Class.
 * Provides access to sub-classes with specified features
 * @author Jannik
 *
 */
public interface Context {
	
	/**
	 * Provides features such as replaceBlock, isBlock, setBlock
	 * @return the Context BlockController
	 */
	@Deprecated
	public BlockController blockController();
	
	
	/**
	 * Provides features such as registerCommand, unregisterCommand, processCommand, processCommandAs
	 * @return the Global CommandController
	 */
	@Deprecated
	public CommandController commandController();
	
	/**
	 * Provides features such as spawnParticle, spawnSound or spawnEffect
	 * @return the Context EffectController
	 */
	public EffectController effectController();
	
	/**
	 * Provides features such as spawnEntity, controlEntity or add Tasks to Entities
	 * @return the Context EntityController
	 */
	@Deprecated
	public EntityController entityController();
	
	/**
	 * Provides features such as registerEvent, callEvent
	 * @return
	 */
	public EventController eventController();
	
	/**
	 * Provides basic features for minigames such as getLobbyLocation, getTeamLocation, endGame or forceStart
	 * @return the Context MinigameController
	 */
	public MinigameController miniGameController();

	/**
	 * Provides features for using nightcloud such as setting status and map
	 * @return cloudController
	 */
	public CloudController cloudController();

	/**
	 * Provides features to load and create maps
	 * @return CraftMapFactory
	 */
	public MapFactory mapFactory();
	
	/**
	 * Gets the plugin associated with this context
	 * @return the plugin
	 */
	public Plugin getPlugin();
	
	/**
	 * Creates a new context
	 * @param pl the plugin
	 * @return the new context
	 */
	public static Context createContext(Plugin pl) {
		return MinigameAPI.instance().createContext(pl);
	}
}
