package de.maltesermailo.impl.minigame.region;

import de.maltesermailo.api.event.IRegionFlagEvent;
import de.maltesermailo.api.region.IRegion;
import de.maltesermailo.api.region.RegionFlag;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @Author Nightloewe
 */
public class RegionFlagEvent extends Event implements IRegionFlagEvent {

    private static final HandlerList handlers = new HandlerList();

    private IRegion region;
    private RegionFlag flag;
    private String flagValue;
    private Player player;

    public RegionFlagEvent(IRegion region, RegionFlag flag, String value, Player p) {
        this.region = region;
        this.flag = flag;
        this.flagValue = value;
        this.player = p;
    }

    @Override
    public IRegion getRegion() {
        return this.region;
    }

    @Override
    public RegionFlag getFlag() {
        return this.flag;
    }

    @Override
    public String getValue() {
        return this.flagValue;
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
