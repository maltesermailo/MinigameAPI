package de.maltesermailo.impl.minigame.gamerules.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class NoChatGameRule extends AbstractGameRule implements Listener {

	public NoChatGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(AsyncPlayerChatEvent event) {
		if(this.isEnabled())
			event.setCancelled(true);
	}

}
