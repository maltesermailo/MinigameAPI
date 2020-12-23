package de.nightloewe.command;

public @interface Command {

	/**
	 * @param Name of command
	 */
	public String name();
	
	/**
	 * @param command description
	 */
	public String description() default "";
	
	/**
	 * @param usage message
	 */
	public String usage() default "";
	
	/**
	 * @param permission to execute command
	 */
	public String permission() default "";
	
	/**
	 * @param permission message when permission is false
	 */
	public String permissionMessage() default "";
	
	/**
	 * @param aliases of the command
	 */
	public String[] aliases();
	
}
