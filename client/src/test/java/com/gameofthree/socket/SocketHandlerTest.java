package com.gameofthree.socket;

import com.gameofthree.event.OnClientMoveEvent;
import com.gameofthree.event.OnServerMessagePublisher;
import com.gameofthree.event.OnStopProcessingEventPublisher;
import com.gameofthree.model.ServerMessage;
import com.gameofthree.model.ServerMessageType;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.CloseStatus;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

class SocketHandlerTest {

    @Test
    void handleTextMessage() {
        var serverMessage = new ServerMessage();
        serverMessage.setMessageType(ServerMessageType.INFO);
        String json = new Gson().toJson(serverMessage);
        var onStopProcessingEventPublisher = Mockito.mock(OnStopProcessingEventPublisher.class);
        var onServerMessagePublisher = Mockito.mock(OnServerMessagePublisher.class);
        SocketHandler socketHandler = new SocketHandler(onStopProcessingEventPublisher, onServerMessagePublisher);
        assertEquals(json, new TextMessage(json).getPayload());
        try {
            socketHandler.handleTextMessage(Mockito.mock(WebSocketSession.class), new TextMessage(json));
        } catch (IOException e) {
            fail();
        }
        verify(onServerMessagePublisher, times(1)).onInfo(any());

    }

    @Test
    void handleTransportError() {
        var onStopProcessingEventPublisher = Mockito.mock(OnStopProcessingEventPublisher.class);
        var onServerMessagePublisher = Mockito.mock(OnServerMessagePublisher.class);
        SocketHandler socketHandler = new SocketHandler(onStopProcessingEventPublisher, onServerMessagePublisher);
        WebSocketSession session = Mockito.mock(WebSocketSession.class);
        socketHandler.handleTransportError(session, new Throwable());
        verify(onStopProcessingEventPublisher, times(1)).publishEvent();
    }

    @Test
    void afterConnectionClosed() {
        var onStopProcessingEventPublisher = Mockito.mock(OnStopProcessingEventPublisher.class);
        var onServerMessagePublisher = Mockito.mock(OnServerMessagePublisher.class);
        SocketHandler socketHandler = new SocketHandler(onStopProcessingEventPublisher, onServerMessagePublisher);
        WebSocketSession session = Mockito.mock(WebSocketSession.class);
        CloseStatus closeStatus = new CloseStatus(1001);
        socketHandler.afterConnectionClosed(session, closeStatus);
        verify(onStopProcessingEventPublisher, times(1)).publishEvent();
    }

    @Test
    void onClientMove() {
        var onStopProcessingEventPublisher = Mockito.mock(OnStopProcessingEventPublisher.class);
        var onServerMessagePublisher = Mockito.mock(OnServerMessagePublisher.class);
        SocketHandler socketHandler = new SocketHandler(onStopProcessingEventPublisher, onServerMessagePublisher);
        socketHandler.afterConnectionEstablished(Mockito.mock(WebSocketSession.class));

        OnClientMoveEvent event = Mockito.mock(OnClientMoveEvent.class);
        socketHandler.onClientMove(event);
        verify(event, times(1)).getMessage();
    }
}
