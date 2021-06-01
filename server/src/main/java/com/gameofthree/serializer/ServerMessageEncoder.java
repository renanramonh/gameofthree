package com.gameofthree.serializer;

import com.gameofthree.model.ServerMessage;
import com.google.gson.Gson;

import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

public class ServerMessageEncoder implements Text<ServerMessage> {
    private final Gson gson = new Gson();

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(ServerMessage serverMessage) {
        return gson.toJson(serverMessage);
    }
}
