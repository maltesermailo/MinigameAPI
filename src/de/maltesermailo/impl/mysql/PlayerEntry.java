package de.maltesermailo.impl.mysql;

public class PlayerEntry {

	private String uuid;
	private String name;
	private String ip;

	private int coins;
	private int joinPower;
	
	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}

	public int getCoins() {
		return coins;
	}

	public int getJoinPower() {
		return joinPower;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setJoinPower(int joinPower) {
		this.joinPower = joinPower;
	}
	
}
