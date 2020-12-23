package de.maltesermailo.impl;

import org.bukkit.command.PluginCommand;

import de.maltesermailo.api.CommandController;
import de.maltesermailo.api.Context;
import de.nightloewe.command.CommandFramework;

public class CraftCommandController implements CommandController {

	private Context ctx;
	private CommandFramework cmdFramework;
	
	public CraftCommandController(Context context) {
		this.ctx = context;
		this.cmdFramework = new CommandFramework(this.ctx.getPlugin());
	}
	
	@Override
	public PluginCommand registerCommand(String name) {
		return null;
	}

	@Override
	public boolean unregisterCommand(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processCommandAs(String asUser, String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerCommands(Object object) {
		return this.cmdFramework.registerCommands(object);
	}

}
