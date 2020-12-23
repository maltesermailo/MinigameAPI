package de.maltesermailo.impl.minigame;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.GamePhase;
import de.maltesermailo.api.minigame.GamePhaseManager;

public class CraftGamePhaseManager implements GamePhaseManager, Runnable {

	private BukkitTask task;
	
	private boolean run;
	
	private HashMap<String, GamePhase> gamePhases = new HashMap<>();
	
	private GamePhase current;

	private Context ctx;
	
	public CraftGamePhaseManager(Context ctx) {
		this.ctx = ctx;
		
		this.task = Bukkit.getScheduler().runTaskTimer(ctx.getPlugin(), this, 1L, 1L);
	}
	
	@Override
	public void registerGamePhase(String name, GamePhase g) {
		this.gamePhases.put(name, g);
	}

	@Override
	public GamePhase current() {
		return this.current;
	}

	@Override
	public GamePhase getGamePhase(String name) {
		return this.gamePhases.get(name);
	}

	@Override
	public void callGamePhase(GamePhase g) {
		if(this.current() != null) {
			this.current().next();
		}
		
		this.ctx.miniGameController().resetGameRules();
		
		this.current = g;
		this.current.init();
		
		System.out.println("GamePhase: " + g);
		
		if(this.current.getCallable() != null) {
			this.current.getCallable().onCall(this.ctx, g);
		}
	}

	@Override
	public void setRunning(boolean run) {
		this.run = run;
		
		if(run) {
			if(task == null) {
				this.task = Bukkit.getScheduler().runTaskTimer(ctx.getPlugin(), this, 1L, 1L);
			}
		} else {
			if(task != null) {
				this.task.cancel();
				this.task = null;
			}
		}
		
		if(this.current != null) {
			this.current.next();
		}
			
		this.current = null;
	}
	
	@Override
	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		if(this.run) {
			if(this.current().whenChange().test(this.ctx)) {
				this.current = this.current().next();
				
				this.ctx.miniGameController().resetGameRules();
				
				if(this.current != null) {
					this.current.init();
					
					if(this.current.getCallable() != null) {
						this.current.getCallable().onCall(this.ctx, this.current);
					}
				}
			}
		}
	}

	
	
}
