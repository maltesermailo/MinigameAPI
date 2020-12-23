package de.maltesermailo.impl.minigame.region;

import de.maltesermailo.api.event.IRegionEvent;
import de.maltesermailo.api.region.IRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @Author Nightloewe
 */
public class RegionEvent extends Event implements IRegionEvent {

    private IRegion currentRegion;
    private Player player;

    private static final HandlerList handlers = new HandlerList();

    public RegionEvent(IRegion region, Player player) {
        this.currentRegion = region;
        this.player = player;
    }

    @Override
    public IRegion getCurrentRegion() {
        return this.currentRegion;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public HandlerList getHandlerList() {
        return handlers;
    }
}
