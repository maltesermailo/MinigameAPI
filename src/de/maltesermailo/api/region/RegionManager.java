package de.maltesermailo.api.region;

import java.util.List;

/**
 * @Author Nightloewe
 */
public interface RegionManager {

	/**
	 * Adds an region to the region manager
	 * @param region
	 * @return given region
	 */
    public IRegion addRegion(IRegion region);

    /**
     * Removes an region from the list
     * @param regionName
     */
    public void removeRegion(String regionName);

    /**
     * Gets the region by given name and returns it
     * @param regionName
     * @return region by Name
     */
    public IRegion getRegion(String regionName);

    /**
     * @return List of regions
     */
    public List<IRegion> getRegions();

}
