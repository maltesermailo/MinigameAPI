package de.maltesermailo.impl.minigame.gamerules;

public class SharedGameRules {
	
	public static enum Block {
		
		BLOCK_BURN, BLOCK_BREAK, BLOCK_PLACE;
		
	}
	
	public static enum Entity {
		NO_DAMAGE, NO_FOOD_LOSE, NO_MOVE;
	}
	
	public static enum Chat {
		NO_CHAT, JOIN_MESSAGE, QUIT_MESSAGE;
	}
	
	public static enum World {
		NO_RAIN, NO_TIME_CHANGE, SETUP_MODE;
	}
}
