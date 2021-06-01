package com.gameofthree.socket;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.gameofthree.model.*;
import com.gameofthree.serializer.ClientMessageDecoder;
import com.gameofthree.serializer.ServerMessageEncoder;
import com.gameofthree.service.GameService;
import com.gameofthree.service.SenderService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/game", decoders = ClientMessageDecoder.class, encoders = ServerMessageEncoder.class)
public class WebSocketEndpoint {
    private final GameService gameService = new GameService(new SenderService());
    Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);
    Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        logger.info("player connected");
        Player player = parserSessionToPlayer(session);
        if (gameService.isNewPlayer(player)) {
            gameService.addNewPlayer(player);
        }
    }

    @OnMessage
    public void onMessage(Session session, ClientMessage clientMessage) {
        Player player = parserSessionToPlayer(session);
        Move move = clientMessage.getMove();
        gameService.processMove(player, move);
        logger.info("move: " + gson.toJson(move));
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("player disconnected");
        gameService.disconnectPlayer(parserSessionToPlayer(session));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error(throwable.getMessage());
        gameService.disconnectPlayer(parserSessionToPlayer(session));
    }

    private Player parserSessionToPlayer(Session session) {
        return new Player(session.getId(), session);
    }

}
