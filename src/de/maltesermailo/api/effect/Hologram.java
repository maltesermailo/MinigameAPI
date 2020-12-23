package de.maltesermailo.api.effect;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface Hologram {

	/**
	 * Deactivates or activates gravity for the Hologram
	 * @param b the boolean to set
	 */
	public void noGravity(boolean b);
	
	/**
	 * Gets the armor stand entity
	 * @return the armorstand
	 */
	public ArmorStand getArmorStand();
	
	/**
	 * Moves the armorstand by calling Navigation#a(double,double,double) : PathEntity
	 * @param l the location
	 */
	public void move(Location l);
	
	/**
	 * Spawns/respawns the entity
	 * @param l the spawn location
	 */
	public void respawn(Location l);
	
	/**
	 * Sends the Hologram per packet
	 * @param p the player
	 * @param l the location
	 */
	public void sendPacket(Player p, Location l);
	
	/**
	 * Teleports the armorstand
	 * @param l the location
	 */
	public void tp(Location l);
	
}
