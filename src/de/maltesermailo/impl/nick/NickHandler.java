package de.maltesermailo.impl.nick;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.commands.NickCommand;
import de.maltesermailo.impl.commands.UnnickCommand;
import de.maltesermailo.impl.mysql.NickEntry;

public class NickHandler {
	
	/**
	 * isSuffixNeeded is used for playlegend because we are using suffixes for chat prefix.
	 * You can set it to true if you are using suffixes for you plugin.
	 */
	private boolean isSuffixNeeded = false;
	private HashMap<String, NickEntry> nickedPlayers = new HashMap<String, NickEntry>();
	
	public NickHandler() {
		NickList.loadNicks();
		Bukkit.getPluginManager().registerEvents(new PlayerNickForPlayerListener(), MinigameAPI.instance());
		Bukkit.getPluginManager().registerEvents(new PlayerSelfColorListener(), MinigameAPI.instance());
		
		Bukkit.getPluginCommand("nick").setExecutor(new NickCommand());
		Bukkit.getPluginCommand("unnick").setExecutor(new UnnickCommand());
	}
	/**
     * Sets the Nick of the given User.
     *
     * @param uuid - UUID of player to set nick.
     * @param nick   - The Nick to set.
     */
	public void setNick(String uuid, String nick) {
		Bukkit.getLogger().info("[MySQL] Updating nick of " + uuid + " to " + nick);
		try(Connection conn = MinigameAPI.instance().mysql().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO legendary_nickcache (uuid, nick) VALUES (?, ?) ON DUPLICATE KEY UPDATE nick = ?");
			stmt.setString(1, uuid);
			stmt.setString(2, nick);
			stmt.setString(3, nick);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes nick from database - only using if deactivating autonick tool
	 * 
	 * @param uuid - UUID of player to remove nick
	 */
	public void removeNick(String uuid) {
		try(Connection conn = MinigameAPI.instance().mysql().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM legendary_nickcache WHERE uuid = ?");
			stmt.setString(1, uuid);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the nick for the given uuid
	 * @param uuid
	 * @return 
	 */
	public String getNick(String uuid) {
		try(Connection conn = MinigameAPI.instance().mysql().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT nick FROM legendary_nickcache WHERE uuid = ?");
			stmt.setString(1, uuid);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.first()) {
				String nick = rs.getString("nick");
				return nick;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns whether nick is free
	 * @param nick
	 * @return true if is free or false if not
	 */
	public boolean isNickFree(String nick) {
		try(Connection conn = MinigameAPI.instance().mysql().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT nick FROM legendary_nickcache WHERE nick = ?");
			stmt.setString(1, nick);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.first()) {
				return false;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean isNicked(String uuid) {
		String nick = this.getNick(uuid);
        if ((nick != null) && (!nick.isEmpty())) {
            return true;
        }
        return false;
	}
	
	/**
	 * Creates nick entry by getting from db
	 * @param String uuid
	 * @return NickEntry
	 */
	public NickEntry createEntryFromDB(String uuid) {
		try(Connection conn = MinigameAPI.instance().mysql().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT nick FROM legendary_nickcache WHERE uuid = ?");
			stmt.setString(1, uuid);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.first()) {
				String nick = rs.getString("nick");
				NickEntry entry = new NickEntry(uuid, nick);
				return entry;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public NickEntry getEntry(String uuid) {
		if(this.nickedPlayers.containsKey(uuid)) {
			return this.nickedPlayers.get(uuid);
		}
		return null;
	}
	
	public void addEntry(NickEntry entry) {
		this.nickedPlayers.put(entry.getUUID(), entry);
	}
	
	public void removeEntry(String uuid) {
		this.nickedPlayers.remove(uuid);
	}
	
	public boolean hasNickEntry(String uuid) {
		return this.nickedPlayers.containsKey(uuid);
	}
	
	public boolean isSuffixNeeded() {
		return isSuffixNeeded;
	}
	public void setSuffixNeeded(boolean isSuffixNeeded) {
		this.isSuffixNeeded = isSuffixNeeded;
	}
	
}
