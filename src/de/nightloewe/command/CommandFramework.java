package de.nightloewe.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.maltesermailo.impl.CraftPluginManager;

public class CommandFramework {

	private Object plugin;
	
	private String standardPermissionMessage = "&cYou don't have permissions to execute this command.";
	
	public CommandFramework(Object plugin) {
		this.plugin = plugin;
	}
	
	public boolean registerCommands(Object obj) {
		for(Method method : obj.getClass().getDeclaredMethods()) {
			for(Annotation annotation : method.getAnnotations()) {
				if(annotation instanceof Command) {
					Command commandAnnotation = (Command) annotation;
					String name = commandAnnotation.name();
					
					String permissionMessage = "";
					if(!commandAnnotation.permissionMessage().isEmpty()) {
						permissionMessage = commandAnnotation.permissionMessage();
					} else {
						permissionMessage = this.standardPermissionMessage;
					}
					
					List<String> aliases = new ArrayList<String>();
					if(commandAnnotation.aliases() != null) {
						aliases = Arrays.asList(commandAnnotation.aliases());
					}
					
					BukkitCommand command = new BukkitCommand(name, commandAnnotation.description(), commandAnnotation.usage(), aliases, method, obj);
					command.setPermission(commandAnnotation.permission());
					command.setPermissionMessage(permissionMessage);
					
					this.registerCommand(command);
				}
			}
		}
		return false;
	}
	
	protected boolean registerCommand(BukkitCommand command) {
		if(Bukkit.getPluginManager() instanceof CraftPluginManager) {
			CraftPluginManager pluginManager = (CraftPluginManager) Bukkit.getPluginManager();
			
			try {
				Field f = pluginManager.getClass().getDeclaredField("pluginManager");
				f.setAccessible(true);
				
				SimplePluginManager bukkitPluginManager = (SimplePluginManager) f.get(pluginManager);
				
				Field field = bukkitPluginManager.getClass().getDeclaredField("commandMap");
				field.setAccessible(true);
				
				SimpleCommandMap map = (SimpleCommandMap) field.get(pluginManager);
				if(this.plugin instanceof JavaPlugin) {
					JavaPlugin plugin = (JavaPlugin) this.plugin;
					
					map.register(command.getName(), plugin.getName(), command);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
