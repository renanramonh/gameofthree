package com.gameofthree.serializer;

import com.gameofthree.model.ClientMessage;
import com.gameofthree.model.Move;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMessageDecoderTest {

    @Test
    void decode() {
        Gson gson = new Gson();
        ClientMessageDecoder decoder = new ClientMessageDecoder();
        ClientMessage message = new ClientMessage(new Move(56, 0));
        String json = gson.toJson(message);

        var decoded = decoder.decode(json);
        assertEquals(message.getMove().getResultingNumber(), decoded.getMove().getResultingNumber());
        assertEquals(message.getMove().getAddedNumber(), decoded.getMove().getAddedNumber());
    }
}
