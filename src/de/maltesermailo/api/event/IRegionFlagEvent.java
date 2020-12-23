package de.maltesermailo.api.event;

import de.maltesermailo.api.region.IRegion;
import de.maltesermailo.api.region.RegionFlag;
import org.bukkit.entity.Player;

/**
 * Represents and event which is called when someone is flagged in a region
 * Check RegionFlags for more information
 * @Author Nightloewe
 */
public interface IRegionFlagEvent {

    public IRegion getRegion();

    public RegionFlag getFlag();

    public String getValue();

    public Player getPlayer();

}
