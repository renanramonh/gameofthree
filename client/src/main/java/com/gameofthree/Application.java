package com.gameofthree;

import com.gameofthree.console.ConsoleProcessor;
import com.gameofthree.event.OnStopProcessingEventPublisher;
import com.gameofthree.socket.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public final OnStopProcessingEventPublisher onStopProcessingEventPublisher;
    private final ConsoleProcessor processor;
    private final SocketHandler socketHandler;
    @Value("${server.uri}") private String host;

    @Autowired
    public Application(
            OnStopProcessingEventPublisher onStopProcessingEventPublisher,
            ConsoleProcessor processor,
            SocketHandler socketHandler
    ) {
        this.onStopProcessingEventPublisher = onStopProcessingEventPublisher;
        this.processor = processor;
        this.socketHandler = socketHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Boolean isAutomaticMode = Boolean.parseBoolean(args[0]);
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(new StandardWebSocketClient(),
                socketHandler, host);
        connectionManager.start();
        processor.start(isAutomaticMode);
    }
}
