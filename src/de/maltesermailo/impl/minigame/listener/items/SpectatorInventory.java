package de.maltesermailo.impl.minigame.listener.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.api.utils.ItemFactory;

public class SpectatorInventory {

	private int page;
	private List<Inventory> inventories;
	private Context ctx;
	
	public SpectatorInventory(Context ctx) {
		this.ctx = ctx;
		this.inventories = new ArrayList<Inventory>();
		
		List<Player> players = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(p);
			
			if(!handle.isSpectator()) {
				players.add(p);
			}
		}
		
		while(players.size() > 0) {
			this.inventories.add(this.createPage(players));
		}
	}
	
	private Inventory createPage(List<Player> players) {
		Inventory inv = null;
		if(players.size() > 36) {
			inv = Bukkit.createInventory(null, 56, ChatColor.translateAlternateColorCodes('&', "&7Spieler beobachten"));
		} else {
			inv = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', "&7Spieler beobachten"));
		}
		
		for(int i = 0; i < (players.size() > 36 ? (players.size() >= 47 ? inv.getSize() - 9 : players.size()) : (players.size() >= 36 ? inv.getSize() : players.size())); i++) {
			Player p = players.get(i);
			inv.setItem(i, ItemFactory.newFactory(Material.SKULL_ITEM, p.getDisplayName(), (byte) 3).skullOwner(p.getDisplayName()).build());
			players.remove(i);
		}
		
		if(players.size() > 36) {
			inv.setItem(55, ItemFactory.newFactory(Material.PAPER, "&a\u00BB").build());
		}
		if(this.inventories.size() > 1) {
			inv.setItem(54, ItemFactory.newFactory(Material.PAPER, "&a\u00AB").build());
		}
		
		return inv;
	}
	
	public List<Inventory> getInventories() {
		return inventories;
	}
	
	public void openInventory(Player p, int page) {
		if(this.inventories.size() > 0) {
			p.openInventory(this.inventories.get(page));
			this.page = page;
		} else {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEs gibt keine Spieler zum beobachten"));
		}
	}
	
	public int getPage() {
		return page;
	}
}
