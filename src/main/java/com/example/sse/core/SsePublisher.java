package com.example.sse.core;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The basic SSE contract which SSE used in publisher scenario.
 */
public interface SsePublisher<T> {

    /**
     * Publish event containing data to all of subscribed emitter.
     *
     * @param data - Data which will be published.
     */
    void publish(T data);

    /**
     * Add emitter to list of subscribed emitter.
     *
     * @param emitter - Emitter which will be subscribed.
     */
    void subscribe(SseEmitter emitter);
}
