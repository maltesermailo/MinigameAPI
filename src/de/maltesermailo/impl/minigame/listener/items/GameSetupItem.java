package de.maltesermailo.impl.minigame.listener.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractMinigame;
import de.maltesermailo.api.minigame.Map;
import de.maltesermailo.api.utils.ItemFactory;

public class GameSetupItem implements Listener {
	
	private Context ctx;
	
	private ItemStack gameSetupItem;
	private Inventory gameSetupInv;
	
	public GameSetupItem(Context ctx) {
		ItemStack is = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cGame-Setup"));
		is.setItemMeta(im);
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cGame-Setup"));
		
		for(int i = 0; i < ctx.mapFactory().getMaps().size(); i++) {
			Map map = ctx.mapFactory().getMaps().get(i);
			ItemStack mapItem = ItemFactory.newFactory(Material.EMPTY_MAP, map.getMapName()).build();
			inv.setItem(i + 27, mapItem);
		}
		
		ItemStack forceStart = ItemFactory.newFactory(Material.REDSTONE_TORCH_ON, "&aSpiel starten").build();
		
		inv.setItem(13, forceStart);
		
		this.gameSetupItem = is;
		this.gameSetupInv = inv;
		this.ctx = ctx;
	}
	
	public ItemStack getItem() {
		Bukkit.getPluginManager().registerEvents(this, this.ctx.getPlugin());
		return this.gameSetupItem;
	}
	
	public Inventory getInventory() {
		return this.gameSetupInv;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getItem() != null 
				&& e.getItem().getType() == Material.REDSTONE_TORCH_ON
				&& e.getItem().getItemMeta().hasDisplayName()
				&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cGame-Setup"))) {
			e.setCancelled(true);
			
			if(!e.getPlayer().hasPermission("minigame.forcemap")) {
				e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to execute this command.");

				return;
			}
			
			e.getPlayer().openInventory(getInventory());
		}
	}
	
	@EventHandler 
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cGame-Setup"))) {
			e.setCancelled(true);
			if(e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&aSpiel starten"))) {
					this.ctx.miniGameController().forceStart();
				} else if(e.getCurrentItem().getType() == Material.EMPTY_MAP) {
					String mapName = e.getCurrentItem().getItemMeta().getDisplayName();
					Map map = this.ctx.mapFactory().getMap(mapName);
					this.ctx.mapFactory().setCurrentMap(map);
					AbstractMinigame minigame = (AbstractMinigame) this.ctx.getPlugin();
					minigame.onMapChange(map);
				}
			}
		}
	}
	
}
