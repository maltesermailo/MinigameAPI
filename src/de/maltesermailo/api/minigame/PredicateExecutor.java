package de.maltesermailo.api.minigame;

import java.util.function.Predicate;

public interface PredicateExecutor {
	
	public <T> Predicate<T> predicate();
	
	public void run();

}
