package de.maltesermailo.impl.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class MapLocation {

	double x = 0;
	double y = 0;
	double z = 0;
	
	float yaw = 0;
	float pitch = 0;
	
	String world = "";
	
	public MapLocation(Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
		this.world = loc.getWorld().getName();
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public String getWorld() {
		return world;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setWorld(String world) {
		this.world = world;
	}
	
	public Location get() {
		World world = Bukkit.getWorld(this.world);
		return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
	public boolean equals(MapLocation loc) {
		if(this.x == loc.getX()
				&& this.y == loc.getY()
				&& this.z == loc.getZ()
				&& this.yaw == loc.getYaw()
				&& this.pitch == loc.getPitch()
				&& this.world.equals(loc.getWorld())) {
			return true;
		}
		return false;
	}
	
}
