package de.maltesermailo.impl.nick;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import code.aterstones.nickapi.events.PlayerSelfColorEvent;
import de.maltesermailo.impl.MinigameAPI;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.MetaData;

public class PlayerSelfColorListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSelfColor(PlayerSelfColorEvent e) {
		LuckPermsApi api = LuckPerms.getApi();
    	User user = api.getUserSafe(e.getPlayer().getUniqueId()).orElse(null);
    	
    	Contexts contexts = null;
    	if(user != null) {
    		contexts = api.getContextForUser(user).orElse(null);
    	}
    	
    	MetaData metaData = null;
    	if(contexts != null) {
    		metaData = user.getCachedData().getMetaData(contexts);
    	}
        
    	int rank = Integer.parseInt(metaData != null ? metaData.getMeta().get("rank") : "99");
        e.setRank(rank);
        
        String prefix = (metaData != null ? metaData.getPrefix() : "&a");
		
		String suffix = ChatColor.translateAlternateColorCodes('&', (metaData != null ? metaData.getPrefix() : "&a"));
		
		if(prefix != null) {
			e.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
		} else {
			e.setPrefix(MinigameAPI.instance().getConfig().getString("messages.nick.prefix"));
		}
		
		if(MinigameAPI.getNickHandler().isSuffixNeeded()) {
			if(suffix != null) {
				e.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
			}
		}
		
		Bukkit.getPluginManager().callEvent(new LegendarySelfColorEvent(e));
	}
}
