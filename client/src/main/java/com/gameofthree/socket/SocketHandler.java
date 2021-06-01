package com.gameofthree.socket;

import com.gameofthree.event.OnClientMoveEvent;
import com.gameofthree.event.OnServerMessagePublisher;
import com.gameofthree.event.OnStopProcessingEventPublisher;
import com.gameofthree.model.ServerMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private WebSocketSession webSocketSession;
    private final Gson gson = new Gson();
    private final OnStopProcessingEventPublisher onStopProcessingEventPublisher;
    private final OnServerMessagePublisher onServerMessagePublisher;

    private final static String TRANSPORT_ERROR_PREFIX = "Error on transport: ";
    private final static String OPPONENT_DISCONNECTED_MSG = "Opponent has disconnected";
    private final static String GAME_WIN_MSG = "You won the game!";
    private final static String GAME_OVER_MSG = "Sorry, you lost the game.";

    @Autowired
    public SocketHandler(
            OnStopProcessingEventPublisher onStopProcessingEventPublisher,
            OnServerMessagePublisher onServerMessagePublisher
    ) {
        this.onStopProcessingEventPublisher = onStopProcessingEventPublisher;
        this.onServerMessagePublisher = onServerMessagePublisher;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        webSocketSession = session;
        ServerMessage message = gson.fromJson(textMessage.getPayload(), ServerMessage.class);
        switch (message.getMessageType()) {
            case INFO:
                onServerMessagePublisher.onInfo(message);
                break;
            case MOVE:
                onServerMessagePublisher.onOpponentMove(message);
                break;
            case GAME_START:
                onServerMessagePublisher.onGameStart(message);
                break;
            case GAME_OVER:
                session.close(CloseStatus.GOING_AWAY.withReason(GAME_OVER_MSG));
                break;
            case GAME_WIN:
                session.close(CloseStatus.GOING_AWAY.withReason(GAME_WIN_MSG));
                break;
            case OPPONENT_DISCONNECTED:
                session.close(CloseStatus.GOING_AWAY.withReason(OPPONENT_DISCONNECTED_MSG));
                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSession = session;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println(TRANSPORT_ERROR_PREFIX + exception.getMessage());
        onStopProcessingEventPublisher.publishEvent();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(status.getReason());
        onStopProcessingEventPublisher.publishEvent();
    }

    @EventListener
    public void onClientMove(OnClientMoveEvent event) {
        var message = event.getMessage();
        try {
            webSocketSession.sendMessage(new TextMessage(gson.toJson(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
