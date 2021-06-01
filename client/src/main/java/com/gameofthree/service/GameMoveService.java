package com.gameofthree.service;

import com.gameofthree.model.Move;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameMoveService {
    public Move computeMove(Move lastMove) {
        int addedNumber = 0;
        int res = lastMove.getResultingNumber() % 3;
        if (res == 1) {
            addedNumber = -1;
        } else if (res == 2) {
            addedNumber = 1;
        }
        return calculateNewMove(lastMove, addedNumber);
    }

    public Move generateInitialMove() {
        return new Move(new Random().nextInt(1000) + 3, 0);
    }

    public Move calculateNewMove(Move lastMove, int addedNumber) {
        return new Move((lastMove.getResultingNumber() + addedNumber) / 3, addedNumber);
    }
}
