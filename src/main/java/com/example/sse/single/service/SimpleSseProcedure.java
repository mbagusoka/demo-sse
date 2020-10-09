package com.example.sse.single.service;

import com.example.sse.core.SseProcedure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class SimpleSseProcedure implements SseProcedure {

    private final Consumer<SseEmitter> action;

    private final ScheduledExecutorService executor;

    private SimpleSseProcedure(Consumer<SseEmitter> action) {
        this.action = action;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public static SimpleSseProcedure valueOf(Consumer<SseEmitter> action) {
        return new SimpleSseProcedure(action);
    }

    @Override
    public void execute(SseEmitter emitter) {
        executor.scheduleWithFixedDelay(() -> action.accept(emitter), 0L, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void cleanUp() {
        executor.shutdown();
        log.info("End emitter [{}] and shutdown executor [{}]", Thread.currentThread(), executor.isShutdown());
    }
}
