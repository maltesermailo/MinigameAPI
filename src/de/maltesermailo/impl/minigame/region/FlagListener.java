package de.maltesermailo.impl.minigame.region;

import de.maltesermailo.api.region.IRegion;
import de.maltesermailo.api.region.RegionFlag;
import de.maltesermailo.impl.MinigameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @Author Nightloewe
 */
public class FlagListener implements Listener {

    /**
     * Event for handling ALLOW_ENTER and ALLOW_EXIT Flag
     * @param e
     */
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();

        Player p = e.getPlayer();

        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= e.getTo().getX() && posB.getX() >= e.getTo().getX()
                    && posA.getZ() <= e.getTo().getZ() && posB.getZ() >= e.getTo().getZ()
                    && posA.getY() <= e.getTo().getY() && posB.getY() >= e.getTo().getY()) {
                Bukkit.getPluginManager().callEvent(new RegionEvent(region, p));

                if(!region.is(RegionFlag.ALLOW_ENTER)) {
                    e.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_ENTER, region.getFlag(RegionFlag.ALLOW_ENTER), p));
                }
            }

            if(posA.getX() <= e.getFrom().getX() && posB.getX() >= e.getFrom().getX()
                    && posA.getZ() <= e.getFrom().getZ() && posB.getZ() >= e.getFrom().getZ()
                    && posA.getY() <= e.getFrom().getY() && posB.getY() >= e.getFrom().getY()) {

                if(!region.is(RegionFlag.ALLOW_EXIT)) {
                    e.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_EXIT, region.getFlag(RegionFlag.ALLOW_EXIT), p));
                }
            }
        }

    }

    /**
     * Event for handling ALLOW_COMMAND Flag
     * @param e
     */
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= p.getLocation().getX() && posB.getX() >= p.getLocation().getX()
                    && posA.getZ() <= p.getLocation().getZ() && posB.getZ() >= p.getLocation().getZ()
                    && posA.getY() <= p.getLocation().getY() && posB.getY() >= p.getLocation().getY()) {
                if(!region.is(RegionFlag.ALLOW_COMMAND)) {
                    e.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_COMMAND, region.getFlag(RegionFlag.ALLOW_COMMAND), p));
                }
            }
        }
    }

    /**
     * Event for handling ALLOW_CHAT Flag
     * @param e
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= p.getLocation().getX() && posB.getX() >= p.getLocation().getX()
                    && posA.getZ() <= p.getLocation().getZ() && posB.getZ() >= p.getLocation().getZ()
                    && posA.getY() <= p.getLocation().getY() && posB.getY() >= p.getLocation().getY()) {
                if(!region.is(RegionFlag.ALLOW_CHAT)) {
                    e.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_CHAT, region.getFlag(RegionFlag.ALLOW_CHAT), p));
                }
            }
        }
    }

    /**
     * Event for handling ALLOW_BUILD Flag
     * @param e
     */
    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= p.getLocation().getX() && posB.getX() >= p.getLocation().getX()
                    && posA.getZ() <= p.getLocation().getZ() && posB.getZ() >= p.getLocation().getZ()
                    && posA.getY() <= p.getLocation().getY() && posB.getY() >= p.getLocation().getY()) {
                if(!region.is(RegionFlag.ALLOW_BUILD)) {
                    e.setBuild(false);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_BUILD, region.getFlag(RegionFlag.ALLOW_BUILD), p));
                }
            }
        }
    }

    /**
     * Event for handling ALLOW_DESTROY Flag
     * @param e
     */
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= p.getLocation().getX() && posB.getX() >= p.getLocation().getX()
                    && posA.getZ() <= p.getLocation().getZ() && posB.getZ() >= p.getLocation().getZ()
                    && posA.getY() <= p.getLocation().getY() && posB.getY() >= p.getLocation().getY()) {
                if(!region.is(RegionFlag.ALLOW_DESTROY)) {
                    e.setCancelled(true);
                    Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_DESTROY, region.getFlag(RegionFlag.ALLOW_DESTROY), p));
                }
            }
        }
    }

    /**
     * Event for handling ALLOW_DAMAGE Flag
     * @param e
     */
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        for(IRegion region : MinigameAPI.getRegionManager().getRegions()) {
            Location posA = region.getPositionA();
            Location posB = region.getPositionB();

            if(posA.getX() <= entity.getLocation().getX() && posB.getX() >= entity.getLocation().getX()
                    && posA.getZ() <= entity.getLocation().getZ() && posB.getZ() >= entity.getLocation().getZ()
                    && posA.getY() <= entity.getLocation().getY() && posB.getY() >= entity.getLocation().getY()) {
                if(!region.is(RegionFlag.ALLOW_DAMAGE)) {
                    e.setCancelled(true);
                    if(entity instanceof Player) {
                        Bukkit.getPluginManager().callEvent(new RegionFlagEvent(region, RegionFlag.ALLOW_DAMAGE, region.getFlag(RegionFlag.ALLOW_DAMAGE), (Player) entity));
                    }
                }
            }
        }
    }

}
