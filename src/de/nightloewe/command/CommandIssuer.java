package de.nightloewe.command;

public class CommandIssuer {
	
	private Object issuer;
	
	public CommandIssuer(Object issuer) {
		this.issuer = issuer;
	}
	
	public <T> T as() {
		try {
			return (T) this.issuer;
		} catch(ClassCastException e) {
			return null;
		}
	}

}
