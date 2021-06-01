package com.gameofthree.serializer;

import com.gameofthree.model.Move;
import com.gameofthree.model.ServerMessage;
import com.gameofthree.model.ServerMessageType;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerMessageEncoderTest {

    @Test
    void encode() {
        Gson gson = new Gson();
        var message = new ServerMessage();
        message.setMessageType(ServerMessageType.MOVE);
        message.setText("foo");
        message.setMove(new Move(56, 0));
        var encoder = new ServerMessageEncoder();
        String expected = gson.toJson(message);

        assertEquals(expected, encoder.encode(message));
    }
}
