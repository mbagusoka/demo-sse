package com.example.sse.core;

import com.example.sse.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
public class SseEngine<S> {

    private final Supplier<S> supplier;
    private final Predicate<S> predicate;
    private final String key;
    private final String status;
    private final SseEmitter emitter;
    private final ScheduledExecutorService executor;

    @Value("${app.sse.timeout:0x7fffffffffffffffL}")
    private long timeout;

    @Value("${app.sse.delay:5}")
    private long delay;

    private SseEngine(Supplier<S> supplier, Predicate<S> predicate, String key) {
        this.supplier = supplier;
        this.predicate = predicate;
        this.key = key;
        this.status = Status.IN_PROGRESS.name();
        this.emitter = invoke();
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public static <S> SseEngine<S> with(Supplier<S> supplier, Predicate<S> predicate, String key) {
        return new SseEngine<>(supplier, predicate, key);
    }

    private SseEmitter invoke() {
        SseEmitter sseEmitter = new SseEmitter(timeout);
        sseEmitter.onCompletion(this::finishEmitter);
        sseEmitter.onTimeout(this::finishEmitter);
        sseEmitter.onError(this::handleError);
        return sseEmitter;
    }

    public void start() {
        try {
            emitter.send(SseEmitter.event().name(key).data(status));
            executor.scheduleWithFixedDelay(this::checkStatus, 0, delay, TimeUnit.SECONDS);
        } catch (IOException e) {
            handleError(e);
        }
    }

    private void finishEmitter() {
        log.info("Finish Emitter [{}]", Thread.currentThread());
        executor.shutdown();
    }

    private void checkStatus() {
        if (predicate.test(supplier.get())) {
            try {
                emitter.send(SseEmitter.event().name(key).data(supplier.get()));
//                emitter.complete();
            } catch (IOException e) {
                handleError(e);
            }
        }
    }

    public SseEmitter get() {
        return emitter;
    }

    private void handleError(Throwable e) {
        log.error("Fail to send event with key [{}]", key, e);
        emitter.completeWithError(e);
    }
}
