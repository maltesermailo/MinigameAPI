package de.maltesermailo.impl.minigame.gamerules.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoDamageGameRule extends AbstractGameRule {
	
	public NoDamageGameRule(Context ctx) {
		super(ctx);
		this.registerEvents();
	}

	@EventHandler
	public void onFire(EntityDamageEvent event) {
		if(this.isEnabled()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(this.isEnabled()) {
			event.setCancelled(true);
		}
	}

}
