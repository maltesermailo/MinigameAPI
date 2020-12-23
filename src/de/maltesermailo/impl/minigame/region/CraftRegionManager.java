package de.maltesermailo.impl.minigame.region;

import de.maltesermailo.api.region.IRegion;
import de.maltesermailo.api.region.RegionManager;
import de.maltesermailo.impl.MinigameAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Nightloewe
 */
public class CraftRegionManager implements RegionManager {

    private HashMap<String, IRegion> regions = new HashMap<String, IRegion>();

    public CraftRegionManager() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new FlagListener(), MinigameAPI.instance());
    }

    @Override
    public IRegion addRegion(IRegion region) {
        regions.put(region.getName(), region);
        return region;
    }

    @Override
    public void removeRegion(String regionName) {
        if(regions.containsKey(regionName)) {
            regions.remove(regionName);
        }
    }

    @Override
    public IRegion getRegion(String regionName) {
        return regions.get(regionName);
    }

    @Override
    public List<IRegion> getRegions() {
        return new ArrayList<IRegion>(regions.values());
    }
}
