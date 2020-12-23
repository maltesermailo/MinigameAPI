package de.maltesermailo.impl.minigame.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerHandle;

public class JoinListener implements Listener {

	private Context ctx;

	public JoinListener(Context ctx) {
		this.ctx = ctx;
	}

	@EventHandler
	public void onFire(PlayerJoinEvent event) {
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if(this.ctx.miniGameController().getPlayerHandle(e.getPlayer()) != null) {
			Bukkit.getScheduler().runTaskAsynchronously(this.ctx.getPlugin(), new Runnable() {

				@Override
				public void run() {
					PlayerHandle handle = JoinListener.this.ctx.miniGameController().getPlayerHandle(e.getPlayer());
					if(handle.isNicked()) {
						handle.unnick();
					}
					handle.save();
					handle.destroy();
				}
				
			});
		}
	}

}
