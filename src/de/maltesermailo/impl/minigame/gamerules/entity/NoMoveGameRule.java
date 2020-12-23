package de.maltesermailo.impl.minigame.gamerules.entity;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoMoveGameRule extends AbstractGameRule {

	public NoMoveGameRule(Context ctx) {
		super(ctx);
		this.registerEvents();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(this.isEnabled()) {
			Location from = event.getFrom();
			Location to = event.getTo();
			
			if(to.getX() != from.getX() || to.getY() != from.getY() || to.getZ() != from.getZ()) {
				event.setCancelled(true);
			}
		}
	}

}
