package de.maltesermailo.api.region;

import org.bukkit.Location;

import java.util.HashMap;

/**
 * Represents a region set with the CraftRegionManager
 * @Author Nightloewe
 */
public interface IRegion {

    public String getName();

    public void setName(String name);

    public Location getPositionA();

    public Location setPositionA(Location posA);

    public Location getPositionB();

    public Location setPositionB(Location posB);

    public String getFlag(RegionFlag flag);

    public String setFlag(RegionFlag flag, String value);

    public boolean is(RegionFlag flag);

    public HashMap<RegionFlag, String> flags();

}
