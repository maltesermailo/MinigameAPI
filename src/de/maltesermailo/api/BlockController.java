package de.maltesermailo.api;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Provides basic block control features such as setBlock and so on.
 * @author Jannik
 */
public interface BlockController {

	public boolean setBlock(Location l, Material mat);
	
	public boolean isBlock(Location l, Material mat);
	
}
