package de.maltesermailo.api.minigame;

import org.bukkit.event.Listener;
import de.maltesermailo.api.Context;

public class AbstractGameRule implements GameRule, Listener {

	private boolean enabled = false;
	
	//private PredicateListener<?> listener;

	private Context ctx;
	
	public AbstractGameRule(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void disable() {
		this.enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public Context getContext() {
		return ctx;
	}

	@Override
	public void registerEvents() {
		this.getContext().eventController().addListener(this);
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public PredicateListener<?> getListener() {
		return listener;
	}

	@Override
	public <T extends Event> void setListener(PredicateListener<T> listener) {
		if(this.listener != null) {
			this.ctx.eventController().unregisterPredicateEvent(listener);
		}
		
		
		T t2 = (T) new PlayerJoinEvent(null, "");
		System.out.println(t2.getClass().getName());
		System.out.println(t2.getEventName());
		
		this.listener = listener;
		
		this.ctx.eventController().<T>addPredicateEvent(listener);
	}*/

}
