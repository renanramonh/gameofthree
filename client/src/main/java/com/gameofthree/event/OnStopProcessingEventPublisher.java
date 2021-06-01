package com.gameofthree.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OnStopProcessingEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OnStopProcessingEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent() {
        OnStopProcessingEvent event = new OnStopProcessingEvent(this);
        applicationEventPublisher.publishEvent(event);
    }
}
