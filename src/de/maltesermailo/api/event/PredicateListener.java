package de.maltesermailo.api.event;

import java.util.function.Predicate;

import org.bukkit.event.Event;

public interface PredicateListener<T extends Event> {
	
	/**
	 * Gets the predicate used to filter the event
	 * @return the predicate by the PredicateListener
	 */
	public Predicate<T> predicate();
	
	/**
	 * Fires the event so the listener can catch it
	 * @param event the event
	 */
	public void onFire(T event);
	
	public Class<T> getEventClass();
	
}
