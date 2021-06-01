package com.gameofthree.service;

import com.gameofthree.repository.PlayerRepository;
import com.gameofthree.model.Move;
import com.gameofthree.model.Player;
import com.gameofthree.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;

import static com.gameofthree.model.ServerMessageType.*;

@Service
public class GameService {
    private final PlayerRepository playerRepository = new PlayerRepository();
    private static final int MAX_PLAYERS = 2;
    private static final String WAIT_OPPONENT_CONNECTION = "Waiting for opponent...";
    private static final String WAIT_OPPONENT_MOVE = "Waiting for opponent move...";
    private static final String GAME_STARTED = "Game started!";
    private static final String WAIT_INITIAL_MOVE = "Waiting for opponent initial move...";
    private final SenderService senderService;

    @Autowired
    public GameService(SenderService senderService) {
        this.senderService = senderService;
    }

    private void sendMessageToOpponent(Player currentPlayer, ServerMessage serverMessage) {
        playerRepository
                .getAll()
                .values()
                .forEach(player -> {
                    if (!player.getId().equals(currentPlayer.getId())) {
                        this.senderService.sendMessage(player, serverMessage);
                    }
                });
    }

    private void sendMessageToAllPlayers(ServerMessage serverMessage) {
        playerRepository
                .getAll()
                .values()
                .forEach((player) -> this.senderService.sendMessage(player, serverMessage));
    }

    public void processMove(Player player, Move move) {
        int resultingNumber = move.getResultingNumber();
        int addedNumber = move.getAddedNumber();
        if (isEndGame(resultingNumber)) {
            this.senderService.sendMessage(player, new ServerMessage(GAME_WIN));
            sendMessageToOpponent(player, new ServerMessage(GAME_OVER));
        } else {
            sendMessageToOpponent(
                    player,
                    new ServerMessage(MOVE, new Move(resultingNumber, addedNumber)));
            this.senderService.sendMessage(player, new ServerMessage(WAIT_OPPONENT_MOVE));
        }
    }

    public void disconnectPlayer(Player player) {
        playerRepository.delete(player.getId());
        sendMessageToOpponent(player, new ServerMessage(OPPONENT_DISCONNECTED));
    }

    public Boolean isNewPlayer(Player player) {
        var players = playerRepository.getAll();
        return !players.containsKey(player.getId());
    }

    public void addNewPlayer(Player player) throws IOException {
        if (hasAllPlayers()) {
            player.getSession().close();
        } else {
            playerRepository.add(player);
            if (hasAllPlayers()) {
                sendMessageToAllPlayers(new ServerMessage(GAME_STARTED));
                this.senderService.sendMessage(player, new ServerMessage(WAIT_INITIAL_MOVE));
                sendMessageToOpponent(player, new ServerMessage(GAME_START));
            } else {
                this.senderService.sendMessage(player, new ServerMessage(WAIT_OPPONENT_CONNECTION));
            }
        }
    }

    private Boolean hasAllPlayers() {
        return playerRepository.getAll().size() == MAX_PLAYERS;
    }

    private Boolean isEndGame(int resultingNumber) {
        return resultingNumber == 1;
    }
}
