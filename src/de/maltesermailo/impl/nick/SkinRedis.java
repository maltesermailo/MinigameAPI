package de.maltesermailo.impl.nick;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import code.aterstones.nickapi.skin.SkinGetter;

import com.mojang.authlib.properties.Property;

import de.maltesermailo.impl.MinigameAPI;

public class SkinRedis extends SkinGetter {

    private Jedis jedis;

    public SkinRedis() {
        this.jedis = MinigameAPI.getJedisController().getJedis();
        refreshConnection();
    }
    
    public void refreshConnection() {
        MinigameAPI.getJedisController().refreshConnection();
    }
    
    public void registerChannel(String id) {
        MinigameAPI.getJedisController().registerChannel(id);
    }
    
    @Override
    public Property getProperty(String skinOwner) {
        String nick = getNormalName(skinOwner);

        Jedis jedis = this.jedis;

        String nickKey = "nick:" + nick.toLowerCase();

        boolean fetchSkin = true;
        if ((jedis.hexists(nickKey, "enter")) && (jedis.hexists(nickKey, "skin_value")) && (jedis.hexists(nickKey, "skin_signature"))) {
            String enter = jedis.hget(nickKey, "enter");
            try {
                long lEnter = Long.parseLong(enter);
                if (System.currentTimeMillis() - lEnter > TimeUnit.DAYS.toMillis(7L)) {
                    fetchSkin = true;
                } else {
                    fetchSkin = false;
                }
            } catch (NumberFormatException e) {
                fetchSkin = true;
            }
        }
        if (!fetchSkin) {
            String name = "textures";
            String value = jedis.hget(nickKey, "skin_value");
            String signature = jedis.hget(nickKey, "skin_signature");
            if ((value != null) && (!value.isEmpty()) && (signature != null) && (!signature.isEmpty())) {
                return new Property(name, value, signature);
            }
            fetchSkin = true;
        }
        if (fetchSkin) {
            Property pro = getSkinProperty(nick);
            if (pro != null) {
                jedis.hset(nickKey, "skin_value", pro.getValue());
                jedis.hset(nickKey, "skin_signature", pro.getSignature());
                jedis.hset(nickKey, "enter", String.valueOf(System.currentTimeMillis()));
            }
            return pro;
        }

        return null;
    }
}
