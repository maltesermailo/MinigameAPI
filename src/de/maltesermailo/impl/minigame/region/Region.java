package de.maltesermailo.impl.minigame.region;

import de.maltesermailo.api.region.IRegion;
import de.maltesermailo.api.region.RegionFlag;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * @Author
 */
public class Region implements IRegion {

    private String name;
    private Location posA;
    private Location posB;
    private HashMap<RegionFlag, String> flags = new HashMap<RegionFlag, String>();

    public Region(String name, Location posA, Location posB) {
        this.name = name;
        this.posA = posA;
        this.posB = posB;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Location getPositionA() {
        return this.posA;
    }

    @Override
    public Location setPositionA(Location posA) {
        this.posA = posA;
        return this.posA;
    }

    @Override
    public Location getPositionB() {
        return this.posB;
    }

    @Override
    public Location setPositionB(Location posB) {
        this.posB = posB;
        return this.posB;
    }

    @Override
    public String getFlag(RegionFlag flag) {
        return this.flags.get(flag);
    }

    @Override
    public String setFlag(RegionFlag flag, String value) {
        return this.flags.put(flag, value);
    }

    @Override
    public boolean is(RegionFlag flag) {
        if(this.flags.containsKey(flag)) {
            return Boolean.parseBoolean(this.flags.get(flag));
        } else {
            return true;
        }
    }

    @Override
    public HashMap<RegionFlag, String> flags() {
        return this.flags;
    }
}
