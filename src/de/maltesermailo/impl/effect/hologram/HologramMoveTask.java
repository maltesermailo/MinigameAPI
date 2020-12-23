package de.maltesermailo.impl.effect.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import de.maltesermailo.api.effect.Hologram;
import de.maltesermailo.api.entity.EntityTask;

public class HologramMoveTask implements EntityTask {

	private Location loc;
	
	private Hologram hologram;
	
	public HologramMoveTask(Hologram hologram) {
		this.hologram = hologram;
	}
	
	@Override
	public boolean shouldBegin(Entity e) {
		return !e.getLocation().equals(loc);
	}

	@Override
	public boolean shouldEnd(Entity e) {
		return e.getLocation().equals(loc);
	}

	@Override
	public boolean run(Entity e) {
		this.hologram.move(loc);
		
		return false;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}

}
