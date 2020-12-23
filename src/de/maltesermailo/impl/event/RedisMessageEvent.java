package de.maltesermailo.impl.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is called, when a RedisMessage is sent.
 */
public class RedisMessageEvent extends Event {

    private String channel;
    private String message;

    public RedisMessageEvent(String channel, String message) {

        this.channel = channel;
        this.message = message;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the Channel of the Message.
     *
     * @return The Channel of the Message involved in the Event.
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Returns the Message of the Event.
     *
     * @return The Message in this Event.
     */
    public String getMessage() {
        return message;
    }
}
