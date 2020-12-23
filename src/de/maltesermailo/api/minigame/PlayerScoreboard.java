package de.maltesermailo.api.minigame;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class PlayerScoreboard {

	private HashMap<Integer, ScoreboardEntry> entries = new HashMap<Integer, ScoreboardEntry>();
	private Scoreboard scoreboard;
	private Objective objective;
	
	public PlayerScoreboard(String objectiveName, String displayName) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		this.objective = this.scoreboard.registerNewObjective(objectiveName, "dummy");
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(displayName);
	}
	
	public int setScore(int id, String text, int score) {
		if(text.length() > 36) {
			text = text.substring(0, 35);
		}
		text = ChatColor.translateAlternateColorCodes('&', text);
		
		ScoreboardEntry entry = new ScoreboardEntry(score, text);
		this.entries.put(id, entry);
		this.objective.getScore(text).setScore(score);
		
		return entry.getScore();
	}
	
	public void removeScore(int id) {
		this.scoreboard.resetScores(this.entries.get(id).getText());
		this.entries.remove(id);
	}
	
	public void removeScore(String text) {
		for(Entry<Integer, ScoreboardEntry> entry : this.entries.entrySet()) {
			if(entry.getValue().getText().equalsIgnoreCase(text)) {
				this.removeScore(entry.getKey());
			}
		}
	}
	
	public Entry<Integer, ScoreboardEntry> getScore(int id) {
		for(Entry<Integer, ScoreboardEntry> entry : this.entries.entrySet()) {
			if(entry.getKey() == id) {
				return entry;
			}
		}
		return null;
	}
	
	public Entry<Integer, ScoreboardEntry> getScore(String sentence) {
		for(Entry<Integer, ScoreboardEntry> entry : this.entries.entrySet()) {
			if(entry.getValue().getText().contains(sentence)) {
				return entry;
			}
		}
		return null;
	}
	
	public int size() {
		return this.entries.size();
	}
	
	public void renameObjective(String objective, String displayName) {
		this.objective.unregister();
		this.objective = this.scoreboard.registerNewObjective(objective, "dummy");
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(displayName);
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	private class ScoreboardEntry {
		
		private int score;
		private String text;
		
		public ScoreboardEntry(int score, String text) {
			this.score = score;
			this.text = text;
		}
		
		public int getScore() {
			return score;
		}
		public String getText() {
			return text;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
}
