package net.atlaspvp.crossservertest.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessageReceivedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String context;
    private Object content;
    private String sender;
    private long timeReceived;

    public MessageReceivedEvent(String context, Object content, String sender) {
        this.context = context;
        this.content = content;
        this.sender = sender;
        this.timeReceived = System.currentTimeMillis();
    }

    public String getContext() {
        return context;
    }

    public Object getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public long getTimeSent() {
        return timeReceived;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
