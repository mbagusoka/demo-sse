package com.example.sse.single.service;

import com.example.sse.core.SseProcedure;
import com.example.sse.thread.SseExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SimpleSseService implements SseProcedureProvider<String> {

    private final SseExecutor executor;

    @Override
    public SseProcedure get(String param) {
        Consumer<SseEmitter> action = getAction(param);
        return SimpleSseProcedure.valueOf(action, executor.get());
    }

    private Consumer<SseEmitter> getAction(String param) {
        return emitter -> sendData(param, emitter);
    }

    private void sendData(String param, SseEmitter emitter) {
        try {
            SseEmitter.SseEventBuilder event = getEvent(param);
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    private SseEmitter.SseEventBuilder getEvent(String param) {
        return SseEmitter
            .event()
            .name(param)
            .id(UUID.randomUUID().toString())
            .data("IN_PROGRESS");
    }
}
