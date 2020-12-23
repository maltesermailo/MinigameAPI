package de.maltesermailo.impl.redis;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import de.maltesermailo.impl.MinigameAPI;
import de.maltesermailo.impl.event.RedisMessageEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Basic Implementation for Jedis on MinigameAPI. Replaces the old Redis Instance in parts in the nicks Package.
 *
 * @Author Jannik Müller
 */
public class JedisController {

    private Jedis jedis;
    private File redisCfgFile;
    private YamlConfiguration redisCfg;
    private MessageListener messenger;

    /**
     * Loads HavocJedis.
     */
    public JedisController() {
        this.redisCfgFile = new File(MinigameAPI.instance().getDataFolder() + File.separator + "redis.yml");
        this.redisCfg = YamlConfiguration.loadConfiguration(this.redisCfgFile);
        this.redisCfg.addDefault("redis.host", "localhost");
        this.redisCfg.addDefault("redis.port", "6379");
        this.redisCfg.addDefault("redis.password", "password");
        this.redisCfg.options().copyDefaults(true);
        try {
            this.redisCfg.save(this.redisCfgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = this.redisCfg.getString("redis.host");
        int port = this.redisCfg.getInt("redis.port");
        this.jedis = new Jedis(host, port);
        this.messenger = new MessageListener();
        refreshConnection();
    }

    /**
     * Returns the Jedis Instance called.
     *
     * @return The Jedis Instance called.
     */
    public Jedis getJedis() {
        return jedis;
    }

    /**
     * Refreshes the Connection of the current Jedis instance.
     */
    public void refreshConnection() {
        if (!this.jedis.isConnected()) {
            this.jedis.connect();
            this.jedis.auth(this.redisCfg.getString("redis.password"));
        }
        System.out.println("[Havoc] Jedis connection established");
    }

    /**
     * Registers the Channel with the given ID.
     *
     * @param id - The Id
     */
    public void registerChannel(String id) {
        refreshConnection();
        this.messenger.proceed(this.jedis.getClient(), id);
    }

    /**
     * Sends a Message on the given Channel with the given Message.
     *
     * @param channel - The Channel
     * @param message - The Message
     */
    public void sendMsg(String channel, String message) {
        this.jedis.publish(channel, message);
    }

    class MessageListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            Bukkit.getPluginManager().callEvent(new RedisMessageEvent(channel, message));
        }
    }
}
