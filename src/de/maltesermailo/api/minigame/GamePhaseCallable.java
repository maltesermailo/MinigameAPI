package de.maltesermailo.api.minigame;

import de.maltesermailo.api.Context;

public interface GamePhaseCallable {
	
	public void onCall(Context ctx, GamePhase g);

}
