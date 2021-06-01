package com.gameofthree.event;

import com.gameofthree.model.ServerMessage;

public class OnOpponentMoveEvent extends OnSeverMessageEvent {
    public OnOpponentMoveEvent(Object source, ServerMessage message) {
        super(source, message);
    }
}
