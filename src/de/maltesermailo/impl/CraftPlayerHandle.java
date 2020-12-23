package de.maltesermailo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import code.aterstones.nickapi.NickAPI;
import de.maltesermailo.api.Context;
import de.maltesermailo.api.minigame.PlayerData;
import de.maltesermailo.api.minigame.PlayerHandle;
import de.maltesermailo.api.minigame.PlayerScoreboard;
import de.maltesermailo.api.utils.ItemFactory;
import de.maltesermailo.impl.mysql.NickEntry;
import de.maltesermailo.impl.mysql.PlayerEntry;
import de.maltesermailo.impl.nick.NickHandler;

public class CraftPlayerHandle implements PlayerHandle {
	
	private Player player;
	private PlayerScoreboard currentScoreboard;
	
	//PLAYER Entry
	private PlayerEntry entry;
	
	private boolean isSpectator;
	private Context ctx;
	private PlayerData playerData;
	
	private List<Consumer<PlayerHandle>> callbacks = new ArrayList<Consumer<PlayerHandle>>();
	
	public CraftPlayerHandle(Context ctx, Player p) {
		this.player = p;
		this.ctx = ctx;
		this.reload();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public PlayerScoreboard getCurrentScoreboard() {
		return currentScoreboard;
	}
	
	public String getUUID() {
		return this.player.getUniqueId().toString();
	}
	
	public boolean isSpectator() {
		return isSpectator;
	}
	
	public void setCurrentScoreboard(PlayerScoreboard currentScoreboard) {
		this.currentScoreboard = currentScoreboard;
		this.getPlayer().setScoreboard(this.currentScoreboard.getScoreboard());
	}
	
	public void setSpectator(boolean isSpectator) {
		this.isSpectator = isSpectator;
		
		this.getPlayer().getActivePotionEffects().clear();
		this.getPlayer().getInventory().clear();
		this.getPlayer().getInventory().setHelmet(null);
		this.getPlayer().getInventory().setChestplate(null);
		this.getPlayer().getInventory().setLeggings(null);
		this.getPlayer().getInventory().setBoots(null);
		this.getPlayer().setHealth(20.0D);
		if(this.ctx.mapFactory().current() != null) {
			this.getPlayer().teleport(this.ctx.mapFactory().current().getSpectatorSpawn());
		}
		this.getPlayer().setGameMode(this.isSpectator ? GameMode.ADVENTURE : GameMode.SURVIVAL);
		this.getPlayer().setAllowFlight(this.isSpectator);
		this.getPlayer().setFlying(this.isSpectator);
		
		if(this.isSpectator) 
			this.player.getInventory().setItem(0, ItemFactory.newFactory(Material.COMPASS, "&7Spieler beobachten").build());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(this.isSpectator) {
				if(!this.ctx.miniGameController().getPlayerHandle(p).isSpectator()) {
					p.hidePlayer(this.player);
				}
			} else {
				p.showPlayer(this.player);
			}
		}
	}
	
	@Override
	public void destroy(String reason) {
		CraftMinigameController controller = (CraftMinigameController) this.ctx.miniGameController();
		controller.destroyHandle(this, reason);
	}
	
	public void destroy() {
		this.destroy("§cYou are no longer in game");
	}

	@Override
	public void setCoins(int coins) {
		if(this.entry != null) {
			this.entry.setCoins(coins);
			this.save();
		}
	}

	@Override
	public int getCoins() {
		if(this.entry != null) {
			return this.entry.getCoins();
		}
		return 0;
	}

	public PlayerEntry getPlayerEntry() {
		return this.entry;
	}

	public void setPlayerEntry(PlayerEntry playerEntry) {
		this.entry = playerEntry;
	}

	@Override
	public void reload() {
		if(MinigameAPI.instance().getConfig().getBoolean("mysql.enabled")) {
			Bukkit.getScheduler().runTaskAsynchronously(this.ctx.getPlugin(), () -> {
				try(Connection conn = this.ctx.miniGameController().mysql().getConnection()) {
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO legendary_players(uuid, name, ip, coins, join_power) VALUES (?,?,?,?,?) "
							+ "ON DUPLICATE KEY UPDATE name = ?, ip = ?");
					stmt.setString(1, this.getUUID());
					stmt.setString(2, this.player.getName());
					stmt.setString(3, this.player.getAddress().getHostString());
					stmt.setInt(4, 0);
					stmt.setInt(5, 0);
					stmt.setString(6, this.player.getName());
					stmt.setString(7, this.player.getAddress().getHostString());
					stmt.executeUpdate();
					stmt.close();
					
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM legendary_players WHERE uuid = ?");
					ps.setString(1, this.getUUID());
					
					ResultSet rs = ps.executeQuery();
					if(rs.first()) {
						this.entry = new PlayerEntry();
						this.entry.setUuid(this.getUUID());
						this.entry.setName(this.player.getName());
						this.entry.setCoins(rs.getInt("coins"));
						this.entry.setIp(rs.getString("ip"));
						this.entry.setJoinPower(rs.getInt("join_power"));
						Bukkit.getLogger().info("Entry for user " + this.player.getName() + " loaded");
					}
					
					conn.close();
					
					for(Consumer<PlayerHandle> callbacks : this.callbacks) {
						callbacks.accept(this);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void save() {
		if(MinigameAPI.instance().getConfig().getBoolean("mysql.enabled")) {
			Bukkit.getScheduler().runTaskAsynchronously(this.ctx.getPlugin(), () -> {
				try(Connection conn = this.ctx.miniGameController().mysql().getConnection()) {
					PreparedStatement stmt = conn.prepareStatement("UPDATE legendary_players SET coins = ?, join_power = ? WHERE uuid = ?");
					stmt.setInt(1, this.entry.getCoins());
					stmt.setInt(2, this.entry.getJoinPower());
					stmt.setString(3, this.entry.getUuid());
					stmt.executeUpdate();
					stmt.close();
					conn.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void nick(String nick) {
		NickHandler handler = MinigameAPI.getNickHandler();
		handler.setNick(this.getUUID(), nick);
		handler.addEntry(new NickEntry(this.getUUID(), nick));
		this.player.setDisplayName(nick);
		NickAPI.refreshPlayer(this.player);
		
		for(Player all : Bukkit.getOnlinePlayers())
			all.showPlayer(this.player);
		
		this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', MinigameAPI.instance().getConfig().getString("messages.nick.nick").replaceAll("%NAME%", nick)));
	}

	@Override
	public void unnick() {
		NickHandler handler = MinigameAPI.getNickHandler();
		handler.setNick(this.getUUID(), "");
		handler.removeEntry(this.getUUID());
		this.player.setDisplayName(this.player.getName());
		NickAPI.refreshPlayer(this.player);
		
		for(Player all : Bukkit.getOnlinePlayers())
			all.showPlayer(this.player);
		
		this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', MinigameAPI.instance().getConfig().getString("messages.nick.unnick")));
	}

	@Override
	public boolean isNicked() {
		NickHandler handler = MinigameAPI.getNickHandler();
		NickEntry entry = handler.getEntry(this.getUUID());
		
		if(entry != null && entry.getNick() != null && !entry.getNick().isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void refreshNick() {
		NickAPI.refreshPlayer(this.player);
	}

	@Override
	public <T> T getPlayerData() {
		return (T) this.playerData;
	}

	@Override
	public void setPlayerData(PlayerData data) {
		this.playerData = data;
	}
	
	@Override
	public void waitForLoading(Consumer<PlayerHandle> callback) {
		this.callbacks.add(callback);
	}
}
