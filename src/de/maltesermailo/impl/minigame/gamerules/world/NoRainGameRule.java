package de.maltesermailo.impl.minigame.gamerules.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoRainGameRule extends AbstractGameRule implements Listener {

	public NoRainGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(WeatherChangeEvent event) {
		if(this.isEnabled())
			if(event.toWeatherState()) {
				event.setCancelled(true);
			}
	}
	
}
