package de.maltesermailo.impl.minigame.gamerules.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class QuitMessageGameRule extends AbstractGameRule implements Listener {
	
	public QuitMessageGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(PlayerQuitEvent event) {
		if(this.isEnabled())
			event.setQuitMessage("§7\u00AB " + event.getPlayer().getDisplayName() + " §7hat das Spiel verlassen.");
		else
			event.setQuitMessage("");
	}

}
