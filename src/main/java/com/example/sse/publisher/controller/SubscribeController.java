package com.example.sse.publisher.controller;

import com.example.sse.core.SsePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SsePublisher<String> publisher;

    @GetMapping
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        publisher.subscribe(emitter);
        return emitter;
    }
}
