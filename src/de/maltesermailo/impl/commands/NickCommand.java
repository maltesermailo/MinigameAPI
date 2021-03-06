package de.maltesermailo.impl.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.maltesermailo.api.Context;
import de.maltesermailo.impl.CraftPlayerHandle;
import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.nick.NickList;

public class NickCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
        	if(cs.hasPermission("nick")) {
        		Player player = (Player) cs;
            	
                //Handler for LobbySystem
                if(MinigameAPI.instance().getConfig().getBoolean("lobbyMode")) return false;
                
                String nick = NickList.getRandomNick();
                if (nick == null) {
                    player.sendMessage("§cNicking is currently unavailable");
                    return false;
                }
                for(Context ctx : MinigameAPI.getContexts()) {
                	CraftPlayerHandle handle = (CraftPlayerHandle) ctx.miniGameController().getPlayerHandle(player);
                	handle.nick(nick);
                }
        	}
        }
		return false;
	}

}
