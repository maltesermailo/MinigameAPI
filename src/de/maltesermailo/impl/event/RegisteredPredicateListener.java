package de.maltesermailo.impl.event;

import java.lang.reflect.Method;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.event.PredicateListener;

public class RegisteredPredicateListener<T extends Event> {

	private Listener listener;
	
	@SuppressWarnings("unused")
	private PredicateListener<T> predicateListener;

	private Context ctx;
	
	public RegisteredPredicateListener(Context ctx, PredicateListener<T> predicateListener) {
		this.ctx = ctx;
		
		this.predicateListener = predicateListener;
		
		System.out.println(predicateListener.getClass().getTypeName());
		
		this.listener = new Listener() {
			
			@EventHandler
			public void on(T event) {
				if(predicateListener.predicate().test(event)) {
					predicateListener.onFire(event);
				}
			}
			
		};
		
		for(Method m : this.listener.getClass().getMethods()) {
			if(m.getName().startsWith("on")) {
				System.out.println(m.getParameterTypes()[0]);
			}
		}
		
		this.register();
	}
	
	public void register() {
		this.ctx.eventController().addListener(this.listener);
	}
	
	public void unregister() {
		this.ctx.eventController().unregisterListener(this.listener);
	}
	
	
	
}
