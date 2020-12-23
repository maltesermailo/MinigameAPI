package de.maltesermailo.api;

/**
 * Provides interface for cloud system based methods
 * @author Jonas
 *
 */
public interface CloudController {

	/**
	 * Setting map for cloud system related Signs
	 * @param map
	 */
	public void setMap(String map);
	
	/**
	 * Setting status for cloud system related Signs
	 * @param status
	 * Recommended states:
	 * Offline - for servers which are offline
	 * Preparing - for servers which are actually booting up
	 * Lobby - for servers in lobby phase
	 * Ingame - for servers which are ingame
	 * Online - for 24/7 online servers like pvp servers, citybuild servers
	 */
	public void setStatus(String status);
	
	/**
	 * Setting motd of the server
	 * @param motd
	 */
	public void setMotd(String motd);
	
	/**
	 * Sends ping to cloud system
	 */
	public void ping();
}
