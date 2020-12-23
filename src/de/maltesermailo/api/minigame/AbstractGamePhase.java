package de.maltesermailo.api.minigame;

import java.util.function.Predicate;

import org.bukkit.event.Event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.event.PredicateListener;

public abstract class AbstractGamePhase implements GamePhase {

	private GamePhaseCallable callable;
	
	private Multimap<Class, PredicateListener> eventListeners = HashMultimap.create();
	
	@Override
	public abstract String getName();

	@Override
	public abstract GamePhase next();

	@Override
	public abstract Predicate<Context> whenChange();

	@Override
	public abstract void init();

	@Override
	public <T extends Event> void addPredicateEvent(Context context, PredicateListener<T> l) {
		
		this.eventListeners.get(l.getEventClass()).add(l);
		/*PredicateListener<T> predicateListener = new PredicateListener<T>() {

			@Override
			public Predicate<T> predicate() {
				return new Predicate<T>() {

					@Override
					public boolean test(T t) {
						return context.miniGameController().getGamePhaseManager().current().equals(AbstractGamePhase.this);
					}
				};
			}

			@Override
			public void onFire(T event) {
				if(l.predicate().test(event)) {
					l.onFire(event);
				}
			}
		};
		
		context.eventController().<T>addPredicateEvent(predicateListener);*/
	}
	
	public void dispatchEvent(Event e) {
		if(!this.eventListeners.containsKey(e.getClass())) {
			return;
		}
		
		for(PredicateListener listener : this.eventListeners.get(e.getClass())) {
			if(listener.predicate().test(e)) {
				listener.onFire(e);
			}
		}
	}
	
	public GamePhaseCallable getCallable() {
		return callable;
	}
	
	public void setCallable(Context ctx, GamePhaseCallable callable) {
		this.callable = callable;
		
		GamePhaseManager gpManager = ctx.miniGameController().getGamePhaseManager();
		
		if(gpManager.current() != null && gpManager.current().equals(this)) {
			callable.onCall(ctx, this);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof AbstractGamePhase && ((AbstractGamePhase)obj).getName() != null && (((AbstractGamePhase)obj).getName().equals(this.getName()));
	}
	

}
