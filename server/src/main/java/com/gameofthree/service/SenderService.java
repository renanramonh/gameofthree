package com.gameofthree.service;

import com.gameofthree.model.Player;
import com.gameofthree.model.ServerMessage;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

@Service
public class SenderService {
    public void sendMessage(Player player, ServerMessage serverMessage) {
        Session session = player.getSession();
        try {
            if (session.isOpen()) session.getBasicRemote().sendObject(serverMessage);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
