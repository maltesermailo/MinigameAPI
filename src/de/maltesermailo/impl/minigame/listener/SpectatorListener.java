package de.maltesermailo.impl.minigame.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.impl.minigame.listener.items.SpectatorInventory;
import net.md_5.bungee.api.ChatColor;

public class SpectatorListener implements Listener {

	private Context ctx;
	private HashMap<Player, SpectatorInventory> inventories = new HashMap<Player, SpectatorInventory>();

	public SpectatorListener(Context ctx) {
		this.ctx = ctx;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(e.getPlayer());
		if(handle.isSpectator()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent e) {
		PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(e.getPlayer());
		if(handle.isSpectator()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockCanBuild(BlockCanBuildEvent e) {
		Block block = e.getBlock();
		Chunk chunk = block.getChunk();
		Entity[] entities = chunk.getEntities();
		
		for(Entity entity : entities) {
			if(entity.getLocation().distanceSquared(block.getLocation()) == 1) {
				e.setBuildable(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntityType() == EntityType.PLAYER) {
			PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle((Player) e.getEntity());
			if(handle.isSpectator()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntityType() == EntityType.PLAYER) {
			PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle((Player) e.getEntity());
			
			PlayerHandle dHandle = null;
			if(e.getDamager().getType() == EntityType.PLAYER) {
				dHandle = this.ctx.miniGameController().getPlayerHandle((Player) e.getDamager());
			}
			
			if(handle.isSpectator() || (dHandle != null && dHandle.isSpectator())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(p);
		
		if(e.getItem() != null
				&& e.getItem().getType() == Material.COMPASS
				&& e.getItem().getItemMeta().hasDisplayName()
				&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&7Spieler beobachten"))) {
			SpectatorInventory inv = new SpectatorInventory(this.ctx);
			inv.openInventory(p, 0);
			this.inventories.put(p, inv);
		}
		
		if(handle.isSpectator()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getClickedInventory().getTitle() != null
				&& e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&7Spieler beobachten"))) {
			e.setCancelled(true);
			if(e.getCurrentItem() != null) {
				if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(ChatColor.translateAlternateColorCodes('&', p.getDisplayName()).equalsIgnoreCase(name)) {
							e.getWhoClicked().teleport(p);
							e.getWhoClicked().closeInventory();
						}
					}
				} else if(e.getCurrentItem().getType() == Material.PAPER 
						&& e.getCurrentItem().getItemMeta().hasDisplayName()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&a\u00BB"))) {
						SpectatorInventory inv = this.inventories.get(e.getWhoClicked());
						inv.openInventory((Player) e.getWhoClicked(), inv.getPage() + 1);
					} else {
						SpectatorInventory inv = this.inventories.get(e.getWhoClicked());
						inv.openInventory((Player) e.getWhoClicked(), inv.getPage() - 1);
					}
				}
			}
		}
		
		Player p = (Player) e.getWhoClicked();
		PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(p);
		if(handle.isSpectator()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCloseInv(InventoryCloseEvent e) {
		if(e.getInventory().getTitle() != null
				&& e.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&7Spieler beobachten"))) {
			this.inventories.remove(e.getPlayer());
		}
	}
}
