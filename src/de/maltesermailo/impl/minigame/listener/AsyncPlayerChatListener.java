package de.maltesermailo.impl.minigame.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;
import net.md_5.bungee.api.ChatColor;

public class AsyncPlayerChatListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		String suffix = "";
		
		String nick = p.getDisplayName();
        if (nick == null || nick.isEmpty()) {
            nick = e.getPlayer().getName();
        }
        
        LuckPermsApi api = LuckPerms.getApi();
    	User user = api.getUserSafe(nick).orElse(null);
    	if(user == null) {
    		suffix = "&a";
    	}
    	
    	Contexts contexts = null;
    	if(user != null) {
    		contexts = api.getContextForUser(user).orElse(null);
    	}
    	
    	MetaData metaData = null;
    	if(contexts != null) {
    		metaData = user.getCachedData().getMetaData(contexts);
    	}
    	
    	suffix = (metaData != null ? metaData.getSuffix() : "&a");
    	
    	e.setFormat(ChatColor.translateAlternateColorCodes('&', suffix) + nick + "§r: " + (e.getPlayer().hasPermission("minigame.chat.color") ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage()));
	}
	
}
