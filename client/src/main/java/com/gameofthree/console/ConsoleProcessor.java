package com.gameofthree.console;

import com.gameofthree.event.*;
import com.gameofthree.model.ClientMessage;
import com.gameofthree.model.Move;
import com.gameofthree.service.GameMoveService;
import com.gameofthree.service.SystemService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleProcessor {

    private final Gson gson = new Gson();
    private final Scanner scanner = new Scanner(System.in);
    private final SystemService systemService;
    private final OnClientMessagePublisher onClientMessagePublisher;
    private final GameMoveService gameMoveService;
    private boolean isAutoAnswer = false;

    private final static String INVALID_INITIAL_MOVE = "Please enter a valid input";
    private final static String INVALID_MOVE = "Invalid input, accepted numbers are {-1,0,1}";
    private final static String INVALID_RESULTING_NUMBER = "Invalid input, resulting number must be integer";
    private final static String GAME_START_INPUT = "Type a number to start the game:";
    private final static String OPPONENT_MOVE_PREFIX = "Opponent move: ";
    private final static String YOUR_MOVE_PREFIX = "Your move: ";
    private final static String YOUR_TURN_INPUT = "Your turn. Valid input {-1,0,1}";

    @Autowired
    public ConsoleProcessor(
            SystemService systemService,
            OnClientMessagePublisher onClientMessagePublisher,
            GameMoveService gameMoveService
    ) {
        this.systemService = systemService;
        this.onClientMessagePublisher = onClientMessagePublisher;
        this.gameMoveService = gameMoveService;
    }

    public void start(Boolean isAutoAnswer) {
        this.isAutoAnswer = isAutoAnswer;
        while (true) {
        }
    }

    @EventListener
    public void onGameStart(OnGameStartEvent onGameOverEvent) {
        println(GAME_START_INPUT);
        Move initialMove;
        if (!isAutoAnswer) {
            initialMove = readInitialMove();
        } else {
            initialMove = gameMoveService.generateInitialMove();
        }
        onClientMessagePublisher.onClientMove(new ClientMessage(initialMove));
    }

    @EventListener
    public void onMove(OnOpponentMoveEvent event) {
        Move move = event.getMessage().getMove();
        println(OPPONENT_MOVE_PREFIX + gson.toJson(move));
        println(YOUR_TURN_INPUT);
        var clientMessage = new ClientMessage(readMove(move));
        println(YOUR_MOVE_PREFIX + gson.toJson(clientMessage.getMove()));
        onClientMessagePublisher.onClientMove(clientMessage);
    }

    @EventListener
    public void onInfo(OnInfoEvent event) {
        println(event.getMessage().getText());
    }

    @EventListener
    public void onStopProcessingEvent(OnStopProcessingEvent event) {
        systemService.stopProcessing();
    }

    private int readUserInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ignored) {
        }
        return readUserInput();
    }

    private Move readInitialMove() {
        int userInput = readUserInput();
        if (userInput >= 3) {
            return new Move(userInput, 0);
        }
        println(INVALID_INITIAL_MOVE);
        return readInitialMove();
    }

    private Move readMove(Move lastMove) {
        Move computedMove = gameMoveService.computeMove(lastMove);
        if (isAutoAnswer) return computedMove;

        int userInput = readUserInput();
        if (userInput != -1 && userInput != 0 && userInput != 1) {
            println(INVALID_MOVE);
            readMove(lastMove);
        }
        var newUserMove = gameMoveService.calculateNewMove(lastMove, userInput);
        if (newUserMove.getAddedNumber() == computedMove.getAddedNumber()) return newUserMove;
        println(INVALID_RESULTING_NUMBER);
        return readMove(lastMove);
    }

    private void println(Object obj) {
        System.out.println(obj);
    }
}
