package de.maltesermailo.impl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerHandle;
import net.md_5.bungee.api.ChatColor;

public class CoinsCommand implements CommandExecutor {

	private Context ctx;

	public CoinsCommand(Context ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			PlayerHandle handle = this.ctx.miniGameController().getPlayerHandle(p);
			
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Deine Coins&r: &a" + handle.getCoins()));
			return true;
		} else {
			cs.sendMessage("Console doesn't have a player handle.");
		}
		return false;
	}

}
