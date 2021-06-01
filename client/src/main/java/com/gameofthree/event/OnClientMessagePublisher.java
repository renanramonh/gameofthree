package com.gameofthree.event;

import com.gameofthree.model.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OnClientMessagePublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OnClientMessagePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void onClientMove(final ClientMessage message) {
        var event = new OnClientMoveEvent(this, message);
        applicationEventPublisher.publishEvent(event);
    }
}
