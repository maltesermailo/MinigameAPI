package de.maltesermailo.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;

public class CraftPluginManager implements PluginManager {

	private PluginManager pluginManager;
	
	public CraftPluginManager() {
		if(!(Bukkit.getServer().getPluginManager() instanceof CraftPluginManager)) {
			this.pluginManager = Bukkit.getPluginManager();
			//Now patching
			Field fieldPluginManager;
			try {
				fieldPluginManager = Bukkit.getServer().getClass().getDeclaredField("pluginManager");
				
				fieldPluginManager.setAccessible(true);
				
				Field modifiers = Field.class.getDeclaredField("modifiers");
				modifiers.setAccessible(true);
				modifiers.set(fieldPluginManager, fieldPluginManager.getModifiers() & ~Modifier.FINAL);
				
				fieldPluginManager.set(Bukkit.getServer(), this);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addPermission(Permission perm) {
		this.pluginManager.addPermission(perm);
	}

	@Override
	public void callEvent(Event event) throws IllegalStateException {
		MinigameAPI.getContexts().forEach(c -> c.eventController().dispatchEvent(event));
		
		this.pluginManager.callEvent(event);
	}

	@Override
	public void clearPlugins() {
		this.pluginManager.clearPlugins();
	}

	@Override
	public void disablePlugin(Plugin plugin) {
		this.pluginManager.disablePlugin(plugin);
	}

	@Override
	public void disablePlugins() {
		this.pluginManager.disablePlugins();
	}

	@Override
	public void enablePlugin(Plugin plugin) {
		this.pluginManager.disablePlugin(plugin);
	}

	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean bool) {
		return this.pluginManager.getDefaultPermSubscriptions(bool);
	}

	@Override
	public Set<Permission> getDefaultPermissions(boolean bool) {
		return this.pluginManager.getDefaultPermissions(bool);
	}

	@Override
	public Permission getPermission(String perm) {
		return this.pluginManager.getPermission(perm);
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(String perm) {
		return this.pluginManager.getPermissionSubscriptions(perm);
	}

	@Override
	public Set<Permission> getPermissions() {
		return this.pluginManager.getPermissions();
	}

	@Override
	public Plugin getPlugin(String name) {
		return this.pluginManager.getPlugin(name);
	}

	@Override
	public Plugin[] getPlugins() {
		return this.pluginManager.getPlugins();
	}

	@Override
	public boolean isPluginEnabled(String name) {
		return this.pluginManager.isPluginEnabled(name);
	}

	@Override
	public boolean isPluginEnabled(Plugin plugin) {
		return this.pluginManager.isPluginEnabled(plugin);
	}

	@Override
	public Plugin loadPlugin(File file)
			throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return this.pluginManager.loadPlugin(file);
	}

	@Override
	public Plugin[] loadPlugins(File dir) {
		return this.pluginManager.loadPlugins(dir);
	}

	@Override
	public void recalculatePermissionDefaults(Permission perm) {
		this.pluginManager.recalculatePermissionDefaults(perm);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3,
			Plugin arg4) {
		this.pluginManager.registerEvent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3,
			Plugin arg4, boolean arg5) {
		// TODO Auto-generated method stub
		this.pluginManager.registerEvent(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void registerEvents(Listener arg0, Plugin arg1) {
		// TODO Auto-generated method stub
		this.pluginManager.registerEvents(arg0, arg1);
	}

	@Override
	public void registerInterface(Class<? extends PluginLoader> arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		this.pluginManager.registerInterface(arg0);
	}

	@Override
	public void removePermission(Permission perm) {
		// TODO Auto-generated method stub
		this.pluginManager.removePermission(perm);
	}

	@Override
	public void removePermission(String perm) {
		// TODO Auto-generated method stub
		this.pluginManager.removePermission(perm);
	}

	@Override
	public void subscribeToDefaultPerms(boolean arg0, Permissible arg1) {
		// TODO Auto-generated method stub
		this.pluginManager.subscribeToDefaultPerms(arg0, arg1);
	}

	@Override
	public void subscribeToPermission(String arg0, Permissible arg1) {
		// TODO Auto-generated method stub
		this.pluginManager.subscribeToPermission(arg0, arg1);
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean arg0, Permissible arg1) {
		// TODO Auto-generated method stub
		this.pluginManager.unsubscribeFromDefaultPerms(arg0, arg1);
	}

	@Override
	public void unsubscribeFromPermission(String arg0, Permissible arg1) {
		// TODO Auto-generated method stub
		this.pluginManager.unsubscribeFromPermission(arg0, arg1);
	}

	@Override
	public boolean useTimings() {
		return this.pluginManager.useTimings();
	}

}
