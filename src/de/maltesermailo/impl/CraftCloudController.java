package de.maltesermailo.impl;

import org.bukkit.Bukkit;

import de.maltesermailo.api.CloudController;
import de.maltesermailo.api.Context;
import de.maltesermailo.connector.SpigotPlugin;
import de.maltesermailo.servercontroller.protocol.packet.PacketPing;

public class CraftCloudController implements CloudController {
	
	private String motd;
	private Context ctx;
	
	public CraftCloudController(Context Context) {
		this.ctx = Context;
	}

	public String getMotd() {
		return motd;
	}

	@Override
	public void setMap(String map) {
		SpigotPlugin.getInstance().setMap(map);
	}

	@Override
	public void setStatus(String status) {
		SpigotPlugin.getInstance().setStatus(status);
	}

	@Override
	public void setMotd(String motd) {
		this.motd = motd;
	}

	@Override
	public void ping() {
		SpigotPlugin.getInstance().sendPing(SpigotPlugin.getInstance().getStatus(), SpigotPlugin.getInstance().getMap());
	}

}
