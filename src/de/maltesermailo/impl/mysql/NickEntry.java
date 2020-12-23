package de.maltesermailo.impl.mysql;

public class NickEntry {
	
	private String uuid;
	
	private String nick;
	
	public NickEntry() {
	}
	
	public NickEntry(String uuid, String nick) {
		this.uuid = uuid;
		this.nick = nick;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
}
