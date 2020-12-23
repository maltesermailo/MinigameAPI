package de.maltesermailo.impl.minigame.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.mysql.NickEntry;
import de.maltesermailo.impl.nick.NickList;

public class AsyncPreLoginListener implements Listener {

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e) {
		String uuid = e.getUniqueId().toString();
		if(MinigameAPI.getNickHandler().getNick(uuid) != null) {
			String nick = NickList.getRandomNick();
			MinigameAPI.getNickHandler().setNick(uuid, nick);
			
			Bukkit.getLogger().info("[MinigameAPI] PreLogin: UUID is " + uuid + " - Nick is " + nick);
			
			NickEntry nickEntry = new NickEntry(uuid, nick);
			MinigameAPI.getNickHandler().addEntry(nickEntry);
		}
	}
}
