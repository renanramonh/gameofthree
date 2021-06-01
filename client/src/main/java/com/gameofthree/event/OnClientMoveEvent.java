package com.gameofthree.event;

import com.gameofthree.model.ClientMessage;
import org.springframework.context.ApplicationEvent;

public class OnClientMoveEvent extends ApplicationEvent {
    private final ClientMessage message;

    public OnClientMoveEvent(Object source, ClientMessage message) {
        super(source);
        this.message = message;
    }
    public ClientMessage getMessage() {
        return message;
    }
}
