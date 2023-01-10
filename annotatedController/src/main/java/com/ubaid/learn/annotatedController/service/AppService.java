package com.ubaid.learn.annotatedController.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppService {
    private static final AtomicInteger initializedCounter = new AtomicInteger();

    public AppService() {
        initializedCounter.incrementAndGet();
    }
    
    public int getInitializedCounter() {
        return initializedCounter.get();
    }
}
