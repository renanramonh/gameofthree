package com.gameofthree.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerMessage {
    private Move move;
    private ServerMessageType messageType;
    private String text;

    public ServerMessage() {

    }

    public ServerMessage(ServerMessageType messageType) {
        this.messageType = messageType;
    }

    public ServerMessage(ServerMessageType messageType, Move move) {
        this.messageType = messageType;
        this.move = move;
    }

    public ServerMessage(String text) {
        this.messageType = ServerMessageType.INFO;
        this.text = text;
    }
}
