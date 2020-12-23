package de.maltesermailo.api;

import org.bukkit.Effect;
import org.bukkit.Location;
//import org.bukkit.Particle;
import org.bukkit.Sound;
//import org.bukkit.boss.BarColor;
//import org.bukkit.boss.BarFlag;
//import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import de.maltesermailo.api.effect.Hologram;
//import de.maltesermailo.api.effect.ManagedBossBar;

public interface EffectController {
	
	/**
	 * Spawns a new particle at the given location
	 * @param l the location
	 * @param p the particle
	 * @return successfully or not
	 */
	//public boolean spawnParticle(Location l, Particle p);
	
	/**
	 * Spawns a new effect at the given location
	 * @param l the location
	 * @param e the effect
	 * @return successfully or not
	 */
	public boolean spawnEffect(Location l, Effect e);
	
	/**
	 * Spawns a new sound at the given location
	 * @param l the location
	 * @param s the sound
	 * @param vol the volume
	 * @param pitch the pitch
	 * @return successfully or not
	 */
	public boolean spawnSound(Location l, Sound s, float vol, float pitch);
	
	/**
	 * Creates a new Managed BossBar
	 * @param title the title
	 * @param barColor the BarColor
	 * @param barStyle the BarStyle
	 * @param barFlags the barflags
	 * @return the managed bossbar
	 */
	//public ManagedBossBar spawnBossBar(String title, BarColor barColor, BarStyle barStyle, BarFlag ...barFlags);
	
	/**
	 * Spawns a new Hologram
	 * @param title the title
	 * @return the Hologram
	 */
	public Hologram createHologram(String title);

	/**
	 * Spawns holograms with the given names
	 * @param l the location
	 * @param noGravity if no gravity
	 * @param names the names
	 * @return the holograms as array
	 */
	public Hologram[] spawnHolograms(Location l, boolean noGravity, String...names);

	/**
	 * Spawns holograms with the given names over the packet way
	 * @param p the player
	 * @param l the location
	 * @param noGravity if no gravity
	 * @param names the names
	 * @return the holograms as array
	 */
	public Hologram[] spawnHolograms(Player p, Location l, boolean noGravity, String...names);

}
