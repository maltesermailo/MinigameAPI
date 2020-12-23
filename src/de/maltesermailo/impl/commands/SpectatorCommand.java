package de.maltesermailo.impl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.impl.minigame.gamephases.LobbyGamePhase;
import net.md_5.bungee.api.ChatColor;

public class SpectatorCommand implements CommandExecutor {

	private Context ctx;

	public SpectatorCommand(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			
			if(p.hasPermission("minigame.spectator")) {
				if(this.ctx.miniGameController().getGamePhaseManager().isRunning() 
						&& !(this.ctx.miniGameController().getGamePhaseManager().current() instanceof LobbyGamePhase)) {
					PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(p);
					
					if(!handle.isSpectator()) {
						handle.setSpectator(true);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDu wurdest in den Spectator-Modus versetzt."));
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDu bist bereits ein Spectator."));
					}
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDer Spectator-Modus ist nur während der Spiel-Phase erlaubt."));
				}
				return true;
			}
		} else {
			cs.sendMessage("Consoles cannot be a spectator");
		}
		return false;
	}

	
}
