package de.maltesermailo.api.event;

import de.maltesermailo.api.region.IRegion;
import org.bukkit.entity.Player;

/**
 * Currently not used, can be called in your plugin
 * @Author Jonas Müller
 */
public interface IRegionEvent {

    public IRegion getCurrentRegion();

    public Player getPlayer();

}
