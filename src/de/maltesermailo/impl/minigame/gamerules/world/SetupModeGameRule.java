package de.maltesermailo.impl.minigame.gamerules.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;
import de.maltesermailo.api.minigame.GameRule;

public class SetupModeGameRule extends AbstractGameRule implements Listener {

	public SetupModeGameRule(Context ctx) {
		super(ctx);
	}
	
	@Override
	public void enable() {
		super.enable();
		
		for(GameRule g : this.getContext().miniGameController().getGameRules().values()) {
			if(g != this) {
				g.disable();
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPermission("minigame.admin")) {
			e.getPlayer().kickPlayer("Currently in maintenance");
		}
	}
	

}
