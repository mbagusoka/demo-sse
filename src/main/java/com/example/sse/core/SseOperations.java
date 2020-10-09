package com.example.sse.core;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The basic SSE operations contract.
 */
public interface SseOperations {

    /**
     * Start the SSE Emitter operation.
     * Ideally this method will send data from server to client.
     */
    void start();

    /**
     * Method to get the current active SSE Emitter.
     * @return {@link SseEmitter}
     */
    SseEmitter get();
}
