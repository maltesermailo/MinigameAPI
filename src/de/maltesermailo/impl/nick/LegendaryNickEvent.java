package de.maltesermailo.impl.nick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import code.aterstones.nickapi.events.PlayerNickForPlayerEvent;

public class LegendaryNickEvent extends Event {

	private PlayerNickForPlayerEvent event;
	
	private final static HandlerList handlers = new HandlerList();

	public LegendaryNickEvent(PlayerNickForPlayerEvent e) {
		this.event = e;
	}
	
	@Override
	public HandlerList getHandlers() {
		return LegendaryNickEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return LegendaryNickEvent.handlers;
	}
	
	public Player getNicking() {
		return this.event.getNicking();
	}
	
	public Player getReceiving() {
		return this.event.getReceiving();
	}
	
	public String getNick() {
		return this.event.getNickTo();
	}
	
	public String getPrefix() {
		return this.event.getPrefix();
	}
	
	public String getSuffix() {
		return this.event.getSuffix();
	}
	
	public int getRank() {
		return this.event.getRank();
	}
	
	public void setNick(String nick) {
		this.event.setNickTo(nick);
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
	
}
