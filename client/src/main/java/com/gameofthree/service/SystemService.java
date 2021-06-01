package com.gameofthree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    private final ApplicationContext appContext;

    @Autowired
    public SystemService(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public void stopProcessing() {
        int exitCode = SpringApplication.exit(appContext, () -> 0);
        System.exit(exitCode);
    }
}
