package com.example.sse.publisher.service;

import com.example.sse.core.SsePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SimplePublisherService implements SsePublisher<String> {

    private static final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Override
    public void publish(String data) {
        emitters.forEach(emitter -> publish(data, emitter));
    }

    private void publish(String data, SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().data(data));
        } catch (IOException e) {
            log.info("Remove Emitter [{}]", emitter);
            emitters.remove(emitter);
        }
    }

    @Override
    public void subscribe(SseEmitter emitter) {
        log.info("Add Emitter [{}]", emitter);
        emitters.add(emitter);
    }
}
