package de.maltesermailo.impl.effect;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
//import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.maltesermailo.api.effect.Hologram;
import de.maltesermailo.impl.effect.hologram.HologramMoveTask;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
/*import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_10_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_10_R1.World;*/
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.World;

public class CraftHologram implements Hologram {
	
	private String name;
	
	private ArmorStand entity;
	
	private boolean gravity = true;
	
	private HologramMoveTask moveTask;
	
	/* Packets */
	
	private boolean sendPacket;
	
	private int packetEntityId;
	
	private EntityPlayer sendPlayer;
	
	private EntityArmorStand nmsEntity;
	
	
	public CraftHologram(String title) {
		this.name = title;
	}

	@Override
	public void noGravity(boolean b) {
		this.gravity = !b;
	}

	@Override
	public ArmorStand getArmorStand() {
		return this.entity;
	}

	@Deprecated
	@Override
	public void move(Location l) {
		if(this.moveTask == null) {
			this.moveTask = new HologramMoveTask(this);
			//Register Task
		}
		
		this.moveTask.setLocation(l);
	}

	@Override
	public void respawn(Location l) {
		if(this.sendPacket && this.nmsEntity != null && this.sendPlayer != null) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.nmsEntity.getId());
			
			this.sendPlayer.playerConnection.sendPacket(packet);
			
			this.sendPlayer = null;
			this.nmsEntity = null;
			
			this.sendPacket = false;
		}
		
		if(this.entity != null) {
			this.entity.remove();
		}
		
		this.entity = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		this.entity.setCustomNameVisible(true);
		this.entity.setCustomName(this.name);
//		this.entity.setInvulnerable(true);
		this.entity.setGravity(this.gravity);
//		this.entity.setAI(false);
//		this.entity.setCollidable(false);
		this.entity.setCanPickupItems(false);
		this.entity.setVisible(false);
	}

	@Override
	public void sendPacket(Player p, Location l) {
		this.sendPacket = true;
		
		this.sendPlayer = ((CraftPlayer)p).getHandle();
		
		World w = ((CraftWorld)l.getWorld()).getHandle();
		
		this.nmsEntity = new EntityArmorStand(w);
		this.nmsEntity.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		this.nmsEntity.setInvisible(true);
//		this.nmsEntity.setInvulnerable(true);
		this.nmsEntity.setCustomNameVisible(true);
		this.nmsEntity.setCustomName(this.name);
//		this.nmsEntity.setNoGravity(!this.gravity);
		
		this.packetEntityId = this.nmsEntity.getId();
		
		PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(this.nmsEntity, this.packetEntityId);
		
		this.sendPlayer.playerConnection.sendPacket(packet);
	}

	@Override
	public void tp(Location l) {
		if(!this.sendPacket && this.entity != null) {
			this.entity.teleport(l);
		} else if(this.sendPacket && this.nmsEntity != null && this.sendPlayer != null) {
			this.nmsEntity.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			
			PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(this.nmsEntity);
			
			this.sendPlayer.playerConnection.sendPacket(packet);
		}
	}
	
}
