package de.maltesermailo.impl.nick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import code.aterstones.nickapi.events.PlayerSelfColorEvent;

public class LegendarySelfColorEvent extends Event {
	
	private PlayerSelfColorEvent event;

	private final static HandlerList handlers = new HandlerList();
	
	public LegendarySelfColorEvent(PlayerSelfColorEvent e) {
		this.event = e;
	}
	
	@Override
	public HandlerList getHandlers() {
		return LegendarySelfColorEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return LegendarySelfColorEvent.handlers;
	}
	
	public void setPrefix(String prefix) {
		this.event.setPrefix(prefix);
	}
	
	public void setSuffix(String suffix) {
		this.event.setSuffix(suffix);
	}
	
	public void setRank(int rank) {
		this.event.setRank(rank);
	}
	
	public Player getPlayer() {
		return this.event.getPlayer();
	}

}
