package de.maltesermailo.impl.minigame.gamerules.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoFoodLoseGameRule extends AbstractGameRule implements Listener {

	public NoFoodLoseGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}
	
	@EventHandler
	public void onFire(FoodLevelChangeEvent event) {
		if(this.isEnabled()) {
			event.setFoodLevel(20);
		}
	}

}
