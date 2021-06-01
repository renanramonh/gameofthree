package com.gameofthree.service;

import com.gameofthree.model.Player;
import com.gameofthree.model.ServerMessage;
import org.hamcrest.core.AnyOf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import java.io.IOException;

import static com.gameofthree.model.ServerMessageType.OPPONENT_DISCONNECTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    void disconnectPlayer() {
        try {
            SenderService senderService = Mockito.mock(SenderService.class);
            Session session1 = Mockito.mock(Session.class);
            when(session1.getId()).thenReturn("1");
            Session session2 = Mockito.mock(Session.class);
            when(session2.getId()).thenReturn("2");
            Player player1 = new Player("foo", session1);
            Player player2 = new Player("bar", session2);

            GameService gameService = new GameService(senderService);
            gameService.addNewPlayer(player1);
            gameService.addNewPlayer(player2);
            gameService.disconnectPlayer(player1);

            verify(senderService, times(6)).sendMessage(any(), any());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void isNewPlayer() {
        SenderService senderService = Mockito.mock(SenderService.class);
        Session session1 = Mockito.mock(Session.class);
        when(session1.getId()).thenReturn("1");
        Session session2 = Mockito.mock(Session.class);
        when(session2.getId()).thenReturn("2");
        Player player1 = new Player("foo", session1);
        Player player2 = new Player("bar", session2);

        GameService gameService = new GameService(senderService);
        try {
            gameService.addNewPlayer(player1);
        } catch (IOException e) {
            fail();
        }
        assertFalse(gameService.isNewPlayer(player1));
        assertTrue(gameService.isNewPlayer(player2));
    }

    @Test
    void addNewPlayer() {
        SenderService senderService = Mockito.mock(SenderService.class);
        Session session1 = Mockito.mock(Session.class);
        when(session1.getId()).thenReturn("1");
        Player player1 = new Player("foo", session1);
        GameService gameService = new GameService(senderService);
        try {
            gameService.addNewPlayer(player1);
        } catch (IOException e) {
            fail();
        }
        verify(senderService, times(1)).sendMessage(any(), any());
    }
}
