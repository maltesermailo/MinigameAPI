package de.maltesermailo.api.minigame;

public interface Countdown {
	
	/**
	 * Checks if this countdown is stopped already by {@link Countdown#stop()}
	 * @return
	 */
	public boolean isStopped();
	
	/**
	 * Stops this countdown immediately making it no longer tick
	 */
	public void stop();
	
	/**
	 * Pauses this countdown if it was'nt paused already or stopped
	 */
	public void pause();
	
	/**
	 * Resumes this countdown if it was paused by {@link Countdown#pause()}
	 */
	public void resume();
	
	/**
	 * Restarts this countdown
	 */
	public void restart();
	
	/**
	 * Will be called when one second passes
	 */
	public void onSecondTick();
	
	/**
	 * Will be called when the time is over or {@link Countdown#stop()} was called
	 */
	public void onStop();
	
	/**
	 * Gets the remaining time in seconds
	 * @return the remaining time
	 */
	public int getRemaining();
	
	/**
	 * Sets the remaining time in seconds
	 * @param seconds the seconds
	 */
	public void setRemaining(int seconds);

}
