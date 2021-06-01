package com.gameofthree.event;

import org.springframework.context.ApplicationEvent;

public class OnStopProcessingEvent extends ApplicationEvent {
    public OnStopProcessingEvent(Object source) {
        super(source);
    }
}
