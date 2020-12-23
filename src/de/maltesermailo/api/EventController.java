package de.maltesermailo.api;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import de.maltesermailo.api.event.PredicateListener;

public interface EventController {

	/**
	 * Registers a new bukkit listener
	 * @param l the listener
	 * @return successfully or not
	 */
	public boolean addListener(Listener l);
	
	/**
	 * Unregisters a bukkit listener
	 * @param l the listener
	 * @return successfully or not
	 */
	public boolean unregisterListener(Listener l);
	
	/**
	 * Adds a predicate event listener
	 * @param l the listener
	 * @return successfully or not
	 */
	public <T extends Event> boolean addPredicateEvent(PredicateListener<T> l);
	
	/**
	 * Unregisters a predicate event listener
	 * @param l the listener
	 * @return successfully or not
	 */
	public <T extends Event> boolean unregisterPredicateEvent(PredicateListener<T> l);
	
	public void dispatchEvent(Event e);
}
