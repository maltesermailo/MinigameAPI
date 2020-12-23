package de.maltesermailo.impl.minigame.gamerules.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class BlockPlaceGameRule extends AbstractGameRule implements Listener {

	public BlockPlaceGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(BlockPlaceEvent event) {
		if(this.isEnabled())
			event.setCancelled(true);
	}

}
