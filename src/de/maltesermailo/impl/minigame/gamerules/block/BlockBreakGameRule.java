package de.maltesermailo.impl.minigame.gamerules.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGameRule;

public class BlockBreakGameRule extends AbstractGameRule implements Listener {

	public BlockBreakGameRule(Context ctx) {
		super(ctx);
		
		this.registerEvents();
	}

	@EventHandler
	public void onFire(BlockBreakEvent event) {
		if(this.isEnabled())
			event.setCancelled(true);
	}

}
