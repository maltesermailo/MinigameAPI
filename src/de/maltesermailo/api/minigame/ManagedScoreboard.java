package de.maltesermailo.api.minigame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.maltesermailo.api.Context;

/**
 * Simple Scoreboard API.
 */
public class ManagedScoreboard {

    private Map<Integer, String> send;
    private Scoreboard wrapper;
    private Objective objective;
    private Objective overheadObjective;

    /**
     * Creates a new Scoreboard with the given Objectname and Title.
     *
     * @param objective            - The Objective Name.
     * @param objectiveDisplayname - The Title of the Scoreboard.
     */
    public ManagedScoreboard(String objective, String objectiveDisplayname) {
        this.wrapper = Bukkit.getScoreboardManager().getNewScoreboard();
        
        this.objective = wrapper.registerNewObjective(objective, "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(objectiveDisplayname);
        
        send = new HashMap<>();
    }

    /**
     * Enables DisplaySlot.BELOW_NAME support for the scoreboard.
     * @param objectiveName - The name of the objective to choose.
     */
    public void enableOverheadScoreboard(String objectiveName){
        overheadObjective = wrapper.registerNewObjective(objectiveName, "dummy");
        overheadObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    /**
     * Updates the overhead score with the given parameters.
     * @param game - The Game instance related to.
     * @param player - The Player to recieve the update ( the owner of the scoreboard)
     * @param displayName - The displayname for the scoreboard
     * @param score - The score for the scoreboard
     */
    public void setOverheadScore(Context ctx, Player player, String displayName, int score){
        overheadObjective.setDisplayName(displayName);
        overheadObjective.getScore(player.getName()).setScore(score);
        
        for(PlayerHandle playerHandle : ctx.miniGameController().getPlayerHandles()){
            //playerHandle.getCurrentScoreboard().updateOverheadScore(player.getName(),score);
        }

    }

    /**
     * Updates the overhead score for another player ( in order to make it visible to others).
     * @param name - the name of the player
     * @param score - the score to set
     */
    public void updateOverheadScore(String name,int score){
       overheadObjective.getScore(name).setScore(score);
    }

    /**
     * Sets the Text of the Scoreboard at the given Index (The Higher The Index, the Higher the Position)
     *
     * @param index - The Index of the Text
     * @param text  - The text itsself
     */
    public void setText(int index, String text) {
        if (!send.containsKey(index) || !send.get(index).equalsIgnoreCase(text)) {
            deleteWithScore(index);
            setText(ChatColor.translateAlternateColorCodes('&', text), index);
        }
    }

    /**
     * Changes the ObjectiveName of the Scoreboard
     *
     * @param objective   - The Name of the Scoreboard
     * @param displayname - The Title to change.
     */
    public void changeObjectiveName(String objective, String displayname) {
        this.objective.unregister();
        this.objective = wrapper.registerNewObjective(objective, displayname);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(displayname);
        
        for (java.util.Map.Entry<Integer, String> e : send.entrySet()) {
            setText(e.getKey(), e.getValue());
        }
    }

    /**
     * Just changing the Displayname.
     *
     * @param displayname
     */
    public void changeObjectiveName(String displayname) {
        this.objective.setDisplayName(displayname);
    }

    /**
     * Attaches the Board to a Player.
     *
     * @param p - The Player to attach.
     */
    public void setBoard(Player p) {
        p.setScoreboard(wrapper);
    }

    /**
     * Sets the Text of the Scoreboard.
     *
     * @param text  - The Text to set.
     * @param index - The index of the Line.
     */
    private void setText(String text, int index) {
        Team team = registerTeam(index + "");
        
        String prefix = "";
        String suffix = "";
        String entry = "";
        
        if (text.length() <= 16) {
            entry = text;
        } else if (text.length() <= 32) {
            prefix = text.substring(0, 16);
            entry = text.substring(16, text.length());
        } else {
            prefix = text.substring(0, 16);
            entry = text.substring(16, 32);
            suffix = text
                    .substring(32, text.length() > 48 ? 48 : text.length());
        }
        
        team.setPrefix(prefix);
        team.addEntry(entry);
        team.setSuffix(suffix);
        
        objective.getScore(entry).setScore(index);
        send.put(index, text);
    }

    /**
     * Removes the given Index.
     *
     * @param i - The Indext to remove.
     */
    public void removeIndex(int i) {
        deleteWithScore(i);
    }

    /**
     * The Size of the Scoreboard.
     *
     * @return The Amount of indexes.
     */
    public int getSize() {
        return send.size();
    }

    /**
     * Uninteresting for api Users.
     *
     * @param teamName
     * @return
     */
    private Team registerTeam(String teamName) {
        Team team = null;
        
        if (wrapper.getTeam(teamName) == null) {
            team = wrapper.registerNewTeam(teamName);
        } else {
            team = wrapper.getTeam(teamName);
        }
        
        return team;
    }

    /**
     * Deletes the given Index and score.
     *
     * @param score - The Score to delete.
     */
    private void deleteWithScore(int score) {
        List<String> remove = new ArrayList<>();
        
        for (String s : wrapper.getEntries()) {
            remove.addAll(wrapper.getScores(s).stream()
                    .filter(Score::isScoreSet).filter(i -> i.getScore() == score).map(i -> s)
                    .collect(Collectors.toList()));
        }
        
        for (String s : remove) {
            wrapper.resetScores(s);
            send.remove(score);
        }
    }
}