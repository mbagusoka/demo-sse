package com.example.sse.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Slf4j
public class SseExecutor {

    private ScheduledExecutorService executor;

    public ScheduledExecutorService get() {
        return executor;
    }

    @PostConstruct
    private void postConstruct() {
        executor = Executors.newSingleThreadScheduledExecutor();
        log.info("SSE Executor Start");
    }

    @PreDestroy
    private void preDestroy() {
        executor.shutdown();
        log.info("SSE Executor Shutdown");
    }
}
