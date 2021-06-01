package com.gameofthree.event;

import com.gameofthree.model.ServerMessage;

public class OnGameStartEvent extends OnSeverMessageEvent {

    public OnGameStartEvent(Object source, ServerMessage message) {
        super(source, message);
    }
}
