package com.vannguyen.SpringBootProject.configurations;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class BrowserLaucher {
    @EventListener(ApplicationReadyEvent.class)
    public void lauchBrowser() {
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("http://localhost:8080/swagger-ui.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
