package de.maltesermailo.impl.minigame.gamephases.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.GamePhase;
import de.maltesermailo.api.minigame.GamePhaseManager;

public class LoginListener implements Listener {

	private Context ctx;
	private GamePhase gamePhase;

	public LoginListener(GamePhase gamePhase, Context ctx) {
		this.gamePhase = gamePhase;
		
		this.ctx = ctx;
	}

	@EventHandler
	public void onFire(PlayerLoginEvent event) {
		GamePhaseManager gpManager = this.ctx.miniGameController().getGamePhaseManager();
		
		if(gpManager.current() != null && gpManager.current().equals(this.gamePhase)) {
			if(Bukkit.getOnlinePlayers().size() == this.ctx.miniGameController().getMaxPlayers() && !event.getPlayer().hasPermission(this.ctx.getPlugin().getConfig().getString("premiumPermission"))) {
				event.disallow(Result.KICK_FULL, this.ctx.getPlugin().getConfig().getString("joinFullMessage"));
			} else if(Bukkit.getOnlinePlayers().size() == this.ctx.miniGameController().getMaxPlayers()) {
				boolean playerKicked = false;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(!p.hasPermission(this.ctx.getPlugin().getConfig().getString("premiumPermission"))) {
						p.kickPlayer(this.ctx.getPlugin().getConfig().getString("premiumKickedMessage"));
						
						playerKicked = true;
					}
				}
				
				if(!playerKicked) {
					event.disallow(Result.KICK_FULL, this.ctx.getPlugin().getConfig().getString("joinFullMessage"));
				} else {
					event.allow();
				}
			} else {
				event.allow();
			}
		} else {
			event.allow();
		}
	}

}
