package com.gameofthree.service;

import com.gameofthree.model.Move;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameMoveServiceTest {
    @ParameterizedTest
    @MethodSource("moveComputeData")
    void computeMove(int rNumber, int addNumber, int rNumberExpected, int addNumberExpected) {
        GameMoveService gameMoveService = new GameMoveService();
        Move move = new Move(rNumber, addNumber);
        Move newMove = gameMoveService.computeMove(move);
        assertEquals(newMove.getAddedNumber(), addNumberExpected);
        assertEquals(newMove.getResultingNumber(), rNumberExpected);
    }

    @Test
    void generateInitialMove() {
        GameMoveService gameMoveService = new GameMoveService();
        Move initialMove = gameMoveService.generateInitialMove();
        assertEquals(initialMove.getAddedNumber(), 0);
        assertTrue(initialMove.getResultingNumber() > 3);
    }

    @Test
    void calculateNewMove() {
        GameMoveService gameMoveService = new GameMoveService();
        Move move = new Move(19, 0);
        Move newMove = gameMoveService.calculateNewMove(move, 1);
        assertEquals(newMove.getAddedNumber(), 1);
        assertEquals(newMove.getResultingNumber(), 6);
    }

    private static Stream<Arguments> moveComputeData() {
        return Stream.of(
                Arguments.of(56, 0, 19, 1),
                Arguments.of(19, 1, 6, -1),
                Arguments.of(6, -1, 2, 0)
        );
    }
}
