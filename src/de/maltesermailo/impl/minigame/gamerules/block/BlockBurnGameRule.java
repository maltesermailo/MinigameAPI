package de.maltesermailo.impl.minigame.gamerules.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class BlockBurnGameRule extends AbstractGameRule implements Listener {

	public BlockBurnGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(BlockBurnEvent event) {
		if(this.isEnabled())
			event.setCancelled(true);
	}

}
