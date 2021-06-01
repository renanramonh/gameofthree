package com.gameofthree.event;

import com.gameofthree.model.ServerMessage;

public class OnInfoEvent extends OnSeverMessageEvent {
    public OnInfoEvent(Object source, ServerMessage message) {
        super(source, message);
    }
}
