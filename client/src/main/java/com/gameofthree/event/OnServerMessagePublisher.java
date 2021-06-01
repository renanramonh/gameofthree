package com.gameofthree.event;

import com.gameofthree.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OnServerMessagePublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OnServerMessagePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void onInfo(final ServerMessage message) {
        var event = new OnInfoEvent(this, message);
        applicationEventPublisher.publishEvent(event);
    }

    public void onOpponentMove(final ServerMessage message) {
        var event = new OnOpponentMoveEvent(this, message);
        applicationEventPublisher.publishEvent(event);
    }

    public void onGameStart(final ServerMessage message) {
        var event = new OnGameStartEvent(this, message);
        applicationEventPublisher.publishEvent(event);
    }
}
