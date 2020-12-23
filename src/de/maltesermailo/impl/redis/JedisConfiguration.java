package de.maltesermailo.impl.redis;

import de.maltesermailo.impl.MinigameAPI;

/**
 * Contains several Methods to check some trigger cfg.
 */
public class JedisConfiguration {

    private static final String KEY_HAVOC_CONFIG = "minigame_api_configuration";
    private static final String FIELD_NICKAPI_ACTIVE = "nickapi_disabled";

    /**
     * Disables the NickAPI.
     *
     * @param isDisabled - True if the NickAPI should be disabled, false otherwise.
     */
    public static void setNickAPIDisabled(boolean isDisabled) {
        MinigameAPI.getJedisController().getJedis().hset(KEY_HAVOC_CONFIG, FIELD_NICKAPI_ACTIVE, isDisabled + "");
    }

    /**
     * Returns true if the NickAPI is disabled, false otherwise.
     *
     * @return Returns true if the NickAPI is disabled, false otherwise.
     */
    public static boolean isNickAPIDisabled() {
        return MinigameAPI.getJedisController().getJedis().hget(KEY_HAVOC_CONFIG, FIELD_NICKAPI_ACTIVE).equals("true");
    }
}
