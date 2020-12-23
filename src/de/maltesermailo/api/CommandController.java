package de.maltesermailo.api;

import org.bukkit.command.PluginCommand;

public interface CommandController {
	
	/**
	 * Registers a new PluginCommand
	 * @param name the name of the command
	 * @return the new plugincommand
	 * @deprecated
	 */
	public PluginCommand registerCommand(String name);
	
	/**
	 * Unregisters a plugincommand
	 * @param name the command
	 * @return successfully or not
	 * @deprecated
	 */
	public boolean unregisterCommand(String name);
	
	/**
	 * Processes a command as console
	 * @param command the command
	 * @return successfully or not
	 * @deprecated
	 */
	public boolean processCommand(String command);
	
	/**
	 * Processes a command as a online user
	 * @param asUser the user
	 * @param command the command
	 * @return successfully or not
	 * @deprecated
	 */
	public boolean processCommandAs(String asUser, String command);
	
	/**
	 * Registers commands in one class with commandframework
	 * Warning: needs @Command annotation.
	 * @param commands class
	 * @return successfully or not
	 */
	public boolean registerCommands(Object object);

}
