package com.example.sse.single.service;

import com.example.sse.core.SseProcedure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class SimpleSseProcedure implements SseProcedure {

    private final Consumer<SseEmitter> action;

    private final ScheduledExecutorService executor;

    private SimpleSseProcedure(Consumer<SseEmitter> action, ScheduledExecutorService executor) {
        this.action = action;
        this.executor = executor;
    }

    public static SimpleSseProcedure valueOf(Consumer<SseEmitter> action, ScheduledExecutorService executor) {
        return new SimpleSseProcedure(action, executor);
    }

    @Override
    public void execute(SseEmitter emitter) {
        executor.scheduleWithFixedDelay(() -> action.accept(emitter), 0L, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void cleanUp() {
        log.info("End emitter [{}]", Thread.currentThread());
    }
}
