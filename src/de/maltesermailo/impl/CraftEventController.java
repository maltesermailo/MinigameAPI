package de.maltesermailo.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.EventController;
import de.maltesermailo.api.event.PredicateListener;
import de.maltesermailo.api.minigame.AbstractGamePhase;

public class CraftEventController implements EventController {

	private Multimap<Class, PredicateListener> eventListeners = HashMultimap.create();

	private Context ctx;
	
	public CraftEventController(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public boolean addListener(Listener l) {
		if(l != null) {
			Bukkit.getServer().getPluginManager().registerEvents(l, this.ctx.getPlugin());
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean unregisterListener(Listener l) {
		HandlerList.unregisterAll(l);
		
		return true;
	}

	@Override
	public <T extends Event> boolean addPredicateEvent(PredicateListener<T> l) {
		if(this.eventListeners.containsEntry(l.getEventClass(), l)) {
			return false;
		}
		
		if(this.eventListeners.containsKey(l.getEventClass())) 
			this.eventListeners.get(l.getEventClass()).add(l);
		else
			this.eventListeners.put(l.getEventClass(), l);	
		
		return true;
	}

	@Override
	public <T extends Event> boolean unregisterPredicateEvent(PredicateListener<T> l) {
		if(this.eventListeners.containsEntry(l.getEventClass(), l)) {
			this.eventListeners.remove(l.getEventClass(), l);
			
			return true;
		}
		
		return false;
	}
	
	public void dispatchEvent(Event e) {
		
		if(this.eventListeners.containsKey(e.getClass())) {
			for(PredicateListener listener : this.eventListeners.get(e.getClass())) {
				System.out.println(listener.getClass().getName());
				
				if(listener.predicate().test(e)) {
					listener.onFire(e);
				}
			}
		}
		
		if(this.ctx.miniGameController().getGamePhaseManager().current() != null) {
			((AbstractGamePhase)this.ctx.miniGameController().getGamePhaseManager().current()).dispatchEvent(e);
		}
	}

}
