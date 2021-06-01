package com.gameofthree.serializer;

import com.gameofthree.model.ClientMessage;
import com.google.gson.Gson;

import javax.websocket.Decoder.Text;
import javax.websocket.EndpointConfig;

public class ClientMessageDecoder implements Text<ClientMessage> {
    private final Gson gson = new Gson();

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public ClientMessage decode(String request) {
        return gson.fromJson(request, ClientMessage.class);
    }
}
