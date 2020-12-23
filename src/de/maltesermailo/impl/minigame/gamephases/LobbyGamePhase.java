package de.maltesermailo.impl.minigame.gamephases;

import java.util.function.Predicate;

import org.bukkit.Bukkit;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.MinigameController;
import de.maltesermailo.api.minigame.AbstractGamePhase;
import de.maltesermailo.api.minigame.AbstractMinigame;
import de.maltesermailo.api.minigame.Countdown;
import de.maltesermailo.api.minigame.GamePhase;
import de.maltesermailo.impl.CraftCloudController;
import de.maltesermailo.impl.minigame.gamephases.listener.JoinListener;
import de.maltesermailo.impl.minigame.gamephases.listener.LoginListener;
import de.maltesermailo.impl.minigame.gamerules.SharedGameRules;

public class LobbyGamePhase extends AbstractGamePhase {

	private AbstractMinigame minigame;
	
	public LobbyGamePhase(AbstractMinigame minigame) {
		this.minigame = minigame;
	}
	
	@Override
	public String getName() {
		return "Lobby";
	}

	@Override
	public GamePhase next() {
		return null;
	}

	@Override
	public Predicate<Context> whenChange() {
		return v -> {return this.minigame.getLobbyTimer() != null && this.minigame.getLobbyTimer().isStopped();};
	}

	@Override
	public void init() {
		this.minigame.context().eventController().addListener(new JoinListener(this, this.minigame.context()));
		this.minigame.context().eventController().addListener(new LoginListener(this, this.minigame.context()));
		
		MinigameController mController = this.minigame.context().miniGameController();
		
		mController.getGameRule(SharedGameRules.Block.BLOCK_BREAK.name()).enable();
		mController.getGameRule(SharedGameRules.Block.BLOCK_PLACE.name()).enable();
		mController.getGameRule(SharedGameRules.Block.BLOCK_BURN.name()).enable();
		mController.getGameRule(SharedGameRules.World.NO_RAIN).enable();
		mController.getGameRule(SharedGameRules.World.NO_TIME_CHANGE).enable();
		mController.getGameRule(SharedGameRules.Chat.JOIN_MESSAGE).enable();
		mController.getGameRule(SharedGameRules.Chat.QUIT_MESSAGE).enable();
		mController.getGameRule(SharedGameRules.Entity.NO_DAMAGE).enable();
		mController.getGameRule(SharedGameRules.Entity.NO_FOOD_LOSE).enable();
		
		if(Bukkit.getPluginManager().isPluginEnabled("CloudSystem")) {
			this.minigame.context().cloudController().setStatus("lobby");
		}
	}

}
