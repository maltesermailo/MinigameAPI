package de.maltesermailo.impl;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
//import org.bukkit.Particle;
import org.bukkit.Sound;
//import org.bukkit.boss.BarColor;
//import org.bukkit.boss.BarFlag;
//import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import de.maltesermailo.api.EffectController;
import de.maltesermailo.api.effect.Hologram;
//import de.maltesermailo.api.effect.ManagedBossBar;
import de.maltesermailo.impl.effect.CraftHologram;

public class CraftEffectController implements EffectController {

	/*@Override
	public boolean spawnParticle(Location l, Particle p) {
		l.getWorld().spawnParticle(p, l, 32);
		
		return true;
	}*/

	@Override
	public boolean spawnEffect(Location l, Effect e) {
		l.getWorld().playEffect(l, e, 4);
		
		return true;
	}

	@Override
	public boolean spawnSound(Location l, Sound s, float vol, float pitch) {
		l.getWorld().playSound(l, s, vol, pitch);
		
		return true;
	}

	/*@Override
	public ManagedBossBar spawnBossBar(String title, BarColor barColor, BarStyle barStyle, BarFlag... barFlags) {
		return null;
	}*/

	@Override
	public Hologram createHologram(String title) {
		return new CraftHologram(title);
	}
	
	@Override
	public Hologram[] spawnHolograms(Location l, boolean noGravity, String...names) {
		List<String> holograms = new ArrayList<String>();
		
		Location lastLocation = null;
		
		for(String s : names) {
			Hologram hologram = this.createHologram(s);
			hologram.noGravity(noGravity);
			
			if(lastLocation != null) {
				hologram.respawn(lastLocation.clone().subtract(0, 1, 0));
			} else {
				lastLocation = l;
				
				hologram.respawn(l);
			}
		}
		
		return holograms.toArray(new Hologram[holograms.size()]);
	}
	
	@Override
	public Hologram[] spawnHolograms(Player p, Location l, boolean noGravity, String...names) {
        List<String> holograms = new ArrayList<String>();
		
		Location lastLocation = null;
		
		for(String s : names) {
			Hologram hologram = this.createHologram(s);
			hologram.noGravity(noGravity);
			
			if(lastLocation != null) {
				hologram.sendPacket(p, lastLocation.clone().subtract(0, 1, 0));
			} else {
				lastLocation = l;
				
				hologram.sendPacket(p, l);
			}
		}
		
		return holograms.toArray(new Hologram[holograms.size()]);
	}

}
