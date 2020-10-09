package com.example.sse.single.service;

import com.example.sse.core.SseProcedure;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class SimpleSseService implements SseProcedureProvider<String> {

    @Override
    public SseProcedure get(String param) {
        Consumer<SseEmitter> action = getAction(param);
        return SimpleSseProcedure.valueOf(action);
    }

    private Consumer<SseEmitter> getAction(String param) {
        return emitter -> sendData(param, emitter);
    }

    private void sendData(String param, SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name(param).data(UUID.randomUUID().toString()));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
