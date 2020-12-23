package de.maltesermailo.impl.minigame.gamephases.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractMinigame;
import de.maltesermailo.api.minigame.GamePhase;
import de.maltesermailo.api.minigame.GamePhaseManager;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.nick.NickList;

public class JoinListener implements Listener {

	private Context ctx;
	private GamePhase gamePhase;

	public JoinListener(GamePhase gamePhase, Context ctx) {
		this.gamePhase = gamePhase;
		
		this.ctx = ctx;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onFire(PlayerJoinEvent event) {
		GamePhaseManager gpManager = this.ctx.miniGameController().getGamePhaseManager();
		
		if(!gpManager.isRunning()) {
			PlayerHandle handle = this.ctx.miniGameController().createHandle(event.getPlayer());
			handle.getPlayer().teleport(this.ctx.miniGameController().getLobbyLocation());
			return;
		}
		
		if(gpManager.current() != null && gpManager.current().equals(this.gamePhase)) {
			PlayerHandle handle = this.ctx.miniGameController().createHandle(event.getPlayer());
			handle.getPlayer().teleport(this.ctx.miniGameController().getLobbyLocation());
			
			if(MinigameAPI.getNickHandler().hasNickEntry(handle.getUUID())) {
				handle.nick(MinigameAPI.getNickHandler().getEntry(handle.getUUID()).getNick());
			}
			
			if(Bukkit.getOnlinePlayers().size() >= this.ctx.getPlugin().getConfig().getInt("minPlayers")) {
				this.ctx.miniGameController().startLobbyTimer();
			}
		} else {
			PlayerHandle handle = this.ctx.miniGameController().createHandle(event.getPlayer());
			handle.setSpectator(true);
		}
		
	}

}
