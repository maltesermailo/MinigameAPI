package de.maltesermailo.api.entity;

import org.bukkit.entity.Entity;

public interface EntityTask {
	
	/**
	 * Checks if it should start this task
	 * @param e the entity
	 * @return should start or not as boolean
	 */
	public boolean shouldBegin(Entity e);
	
	/**
	 * Checks if it should stop this task now
	 * @param e the entity
	 * @return should stop or not as boolean
	 */
	public boolean shouldEnd(Entity e);
	
	/**
	 * Run per tick
	 * @param e the entity
	 * @return a boolean if it was completed successfully
	 */
	public boolean run(Entity e);

}
