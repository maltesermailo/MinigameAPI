package de.maltesermailo.impl.minigame.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.maltesermailo.api.Context;

public class LoginListener implements Listener {

	private Context ctx;

	public LoginListener(Context ctx) {
		this.ctx = ctx;
	}
	
	@EventHandler
	public void onFire(PlayerLoginEvent event) {
		if(!this.ctx.miniGameController().getGamePhaseManager().isRunning()) {
			event.disallow(Result.KICK_OTHER, "§cSetting up minigame...");
		}
	}

}
