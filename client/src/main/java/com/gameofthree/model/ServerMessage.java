package com.gameofthree.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServerMessage {
    private Move move;
    private ServerMessageType messageType;
    private String text;
}
