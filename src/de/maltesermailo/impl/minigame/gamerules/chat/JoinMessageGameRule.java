package de.maltesermailo.impl.minigame.gamerules.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class JoinMessageGameRule extends AbstractGameRule implements Listener {
	
	public JoinMessageGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(PlayerJoinEvent event) {
		if(this.isEnabled())
			event.setJoinMessage("§7\u00BB " + event.getPlayer().getDisplayName() + " §7hat das Spiel betreten.");
		else
			event.setJoinMessage("");
	}

}
