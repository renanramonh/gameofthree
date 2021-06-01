package com.gameofthree.event;

import com.gameofthree.model.ServerMessage;
import org.springframework.context.ApplicationEvent;

public abstract class OnSeverMessageEvent extends ApplicationEvent {
    private final ServerMessage message;

    public OnSeverMessageEvent(Object source, ServerMessage message) {
        super(source);
        this.message = message;
    }
    public ServerMessage getMessage() {
        return message;
    }
}
