package de.maltesermailo.api.minigame;

import java.util.function.Predicate;

import org.bukkit.event.Event;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.EventController;
import de.maltesermailo.api.event.PredicateListener;

public interface GamePhase {

	/**
	 * @return the name of the game phase
	 */
	public String getName();
	
	/**
	 * Do some cleanup like unregister a listener and return the next game phase
	 * @return the next game phase
	 */
	public GamePhase next();
	
	/**
	 * When change to the next gamephase given by {@link GamePhase#next()}
	 */
	public Predicate<Context> whenChange();
	
	/**
	 * Do some initialization work like registering listeners
	 */
	public void init();
	
	/**
	 * Get callbock on GamePhase active.
	 * @return the callback
	 */
	public GamePhaseCallable getCallable();
	
	/**
	 * Set callback on GamePhase active.
	 * @param callable the callback
	 */
	public void setCallable(Context ctx, GamePhaseCallable callable);
	
	/**
	 * Short link to {@link EventController#addPredicateEvent(PredicateListener)} but with save of the listener to unregister
	 * @param <T>
	 * @param l the predicate listener
	 */
	public <T extends Event> void addPredicateEvent(Context context, PredicateListener<T> l);
	
}
