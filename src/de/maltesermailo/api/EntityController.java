package de.maltesermailo.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.maltesermailo.api.entity.EntityTask;

/**
 * Entity Controller provides Entity Features such as spawnEntity, controlEntity or Add Tasks to Entity
 * @author Jannik
 *
 */
public interface EntityController {

	/**
	 * Spawns a new controllable entity with the givent ype
	 * @param loc the location
	 * @param type the entitytype
	 * @return the entity
	 */
	public Entity spawnEntity(Location loc, EntityType type);
	
	/**
	 * Moves a Entity to the given location normally
	 * @param e the entity
	 * @param loc the location
	 */
	public void moveEntity(Entity e, Location loc);
	
	/**
	 * Adds a task to an entity
	 * @param e the entity
	 * @param task the entitytask
	 */
	public void addTask(Entity e, EntityTask task);
	
}
