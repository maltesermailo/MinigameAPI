package de.maltesermailo.impl.minigame.gamerules.world;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoTimeChangeGameRule extends AbstractGameRule {

	private BukkitTask bukkitTask;
	
	public NoTimeChangeGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@Override
	public void enable() {
		super.enable();
		
		if(this.bukkitTask == null) {
			this.bukkitTask = Bukkit.getScheduler().runTaskTimer(this.getContext().getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(w -> w.setFullTime(0L));
				}
			}, 1L, 1200L);
		}
	}
	
	@Override
	public void disable() {
		super.disable();
		
		if(this.bukkitTask != null) {
			this.bukkitTask.cancel();
			this.bukkitTask = null;
		}
	}
	
	
}
