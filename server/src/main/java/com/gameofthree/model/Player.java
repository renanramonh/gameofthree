package com.gameofthree.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;

@Getter @Setter @AllArgsConstructor
public class Player {
    private String id;
    private Session session;
}
