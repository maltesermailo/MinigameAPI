package de.maltesermailo.api.example;

import java.util.function.Predicate;

import org.bukkit.Bukkit;

import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.AbstractGamePhase;
import de.maltesermailo.api.minigame.AbstractMinigame;
import de.maltesermailo.api.minigame.GamePhase;
import de.maltesermailo.api.minigame.Map;

public class ExampleMinigame extends AbstractMinigame {

	@Override
	public void enable() {
		this.context().miniGameController().getGamePhaseManager().registerGamePhase("lobby", new AbstractGamePhase() {
			
			@Override
			public Predicate<Context> whenChange() {
				return new Predicate<Context>() {

					@Override
					public boolean test(Context t) {
						return (Bukkit.getOnlinePlayers().size() >= getConfig().getInt("minPlayers"));
					}
				};
			}
			
			@Override
			public GamePhase next() {
				return null;
			}
			
			@Override
			public void init() {
				
			}
			
			@Override
			public String getName() {
				return "lobby";
			}

			
		});
	}
	
	@Override
	public void onSecondTick(int remaining) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapChange(Map map) {
		// TODO Auto-generated method stub
		
	}

}
