package de.maltesermailo.api.minigame;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import de.maltesermailo.api.Context;

public abstract class AbstractCountdown implements Countdown, Runnable {

	private BukkitTask task;
	
	private int remainingTime;
	
	private boolean paused = false;

	private Context ctx;
	
	public AbstractCountdown(Context context, int timeInSeconds) {
		this.remainingTime = timeInSeconds;
		
		this.ctx = context;
		
		this.task = Bukkit.getScheduler().runTaskTimer(context.getPlugin(), this, 0L, 20L);
	}
	
	@Override
	public boolean isStopped() {
		return this.task == null;
	}

	@Override
	public void stop() {
		this.task.cancel();
		this.task = null;
		
		this.onStop();
	}

	@Override
	public void pause() {
		this.paused = true;
	}

	@Override
	public void resume() {
		this.paused = false;
	}
	
	@Override
	public void restart() {
		this.task.cancel();
		
		this.task = Bukkit.getScheduler().runTaskTimer(this.ctx.getPlugin(), this, 20L, 20L);
	}
	
	@Override
	public int getRemaining() {
		return this.remainingTime;
	}
	
	@Override
	public void setRemaining(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	@Override
	public abstract void onSecondTick();

	@Override
	public abstract void onStop();

	@Override
	public void run() {
		if(!this.paused) {
			this.remainingTime--;
			
			this.onSecondTick();
			
			if(this.remainingTime <= 0) {
				this.stop();
			}
		}
	}

}
