package de.maltesermailo.impl.nick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import code.aterstones.nickapi.events.PlayerNickForPlayerEvent;
import de.maltesermailo.impl.MinigameAPI;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;

public class PlayerNickForPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNickChange(PlayerNickForPlayerEvent e) {
		//Getting a random  nick
		String nick = MinigameAPI.getNickHandler().getNick(e.getNicking().getUniqueId().toString());
		
		if(MinigameAPI.getNickHandler().getEntry(nick) != null 
				&& !MinigameAPI.getNickHandler().getEntry(nick).getNick().isEmpty()) {
			//Checking nick for existence
			nick = checkNick(nick);
			MinigameAPI.getNickHandler().setNick(e.getNicking().getUniqueId().toString(), nick);
			MinigameAPI.getNickHandler().getEntry(e.getNicking().getUniqueId().toString()).setNick(nick);
		}
		LuckPermsApi api = LuckPerms.getApi();
		
		Bukkit.getLogger().info("Nick for user " + e.getNicking().getName() + ": " + nick);
		
		if(nick != null && !nick.isEmpty()) {
			if(!MinigameAPI.instance().getConfig().getBoolean("lobbyMode")) {
				Bukkit.getLogger().info("Nicking player " + e.getNicking().getName());
				User user = api.getUserSafe(nick).orElse(null);
            	
            	Contexts contexts = null;
            	if(user != null) {
            		contexts = api.getContextForUser(user).orElse(null);
            	}
            	
            	MetaData metaData = null;
            	if(contexts != null) {
            		metaData = user.getCachedData().getMetaData(contexts);
            	}
            	
				int rank;
				
				if(metaData != null) {
					rank = Integer.parseInt(metaData.getMeta().get("rank"));
				} else {
					rank = 99;
				}
				
				//Setting rank
				e.setRank(rank);
				
				//Handle nick related things.
				e.setNickTo(nick);
				e.setSuffix(MinigameAPI.instance().getConfig().getString("messages.nick.prefix"));
				
				//If user has permission to see nicks, set real username
				//otherwise set prefix
				if(e.getReceiving().hasPermission("nick.see")) {
					Bukkit.getLogger().info(e.getReceiving().getName() + " has nick.see perm.");
					
					user = api.getUserSafe(e.getNicking().getUniqueId()).orElse(null);
	            	
	            	if(user != null) {
	            		contexts = api.getContextForUser(user).orElse(null);
	            	}
	            	
	            	if(contexts != null) {
	            		metaData = user.getCachedData().getMetaData(contexts);
	            	}
					
					e.setPrefix(ChatColor.translateAlternateColorCodes('&', (metaData != null ? metaData.getPrefix() : MinigameAPI.instance().getConfig().getString("messages.nick.prefix"))));
					e.setRank((metaData != null ? Integer.parseInt(metaData.getMeta().get("rank")) : 99));
					e.setNickTo(e.getNicking().getName());
				} else {
					Bukkit.getLogger().info(e.getReceiving().getName() + " has NOT nick.see perm.");
					e.setPrefix(ChatColor.translateAlternateColorCodes('&', (metaData != null ? metaData.getPrefix() : MinigameAPI.instance().getConfig().getString("messages.nick.prefix"))));
				}
			} else {
				Bukkit.getLogger().info("[NICK] LobbyMode enabled");
				User user = api.getUserSafe(nick).orElse(null);
            	
            	Contexts contexts = null;
            	if(user != null) {
            		contexts = api.getContextForUser(user).orElse(null);
            	}
            	
            	MetaData metaData = null;
            	if(contexts != null) {
            		metaData = user.getCachedData().getMetaData(contexts);
            	}
            	
				int rank;
				
				if(metaData != null) {
					rank = Integer.parseInt(metaData.getMeta().get("rank"));
				} else {
					rank = 99;
				}
				e.setPrefix(ChatColor.translateAlternateColorCodes('&', (metaData != null ? metaData.getPrefix() : MinigameAPI.instance().getConfig().getString("messages.nick.prefix"))));
				
				e.setRank(rank);
			}
		} else {
			//Getting user from luckperms
			User user = api.getUserSafe(e.getNicking().getUniqueId()).orElse(null);
        	
        	Contexts contexts = null;
        	if(user != null) {
        		contexts = api.getContextForUser(user).orElse(null);
        	}
        	
        	MetaData metaData = null;
        	if(contexts != null) {
        		metaData = user.getCachedData().getMetaData(contexts);
        	}
        	
        	//Setting rank and prefix for tablist
			int rank;
			
			if(metaData != null) {
				rank = Integer.parseInt(metaData.getMeta().get("rank"));
			} else {
				rank = 99;
			}
			e.setPrefix(ChatColor.translateAlternateColorCodes('&', (metaData != null ? metaData.getPrefix() : MinigameAPI.instance().getConfig().getString("messages.nick.prefix"))));
			
			e.setRank(rank);
		}
		
		Bukkit.getPluginManager().callEvent(new LegendaryNickEvent(e));
	}

	private String checkNick(String nick) {
		if(!NickList.getNames().contains(nick)) {
			return NickList.getRandomNick();
		}
		return nick;
	}
}
