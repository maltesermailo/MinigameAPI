package de.nightloewe.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.command.CommandSender;

public class BukkitCommand extends org.bukkit.command.Command {

	private Method method;
	private Object invokeClass;
	
	public BukkitCommand(String name, Method method, Object invokeClass) {
		super(name);
		this.method = method;
		this.invokeClass = invokeClass;
	}
	
	public BukkitCommand(String name, String description, String usageMessage, List<String> aliases, Method method, Object invokeClass) {
		super(name, description, usageMessage, aliases);
		this.method = method;
		this.invokeClass = invokeClass;
	}

	@Override
	public boolean execute(CommandSender commandSender, String commandLabel, String[] commandArgs) {
		try {
			return (Boolean) this.method.invoke(this.invokeClass, new CommandIssuer(commandSender), commandLabel, commandArgs);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if(e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
		}
		return false;
	}

}
