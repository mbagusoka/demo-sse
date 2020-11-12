package com.example.sse.core;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The basic procedure to execute SSE procedures.
 * This contract mainly will be used as contract SSE's execution used in {@link SseTemplate}
 */
public interface SseProcedure {

    /**
     * Execute set of action defined in implementation class.
     * Ideally this method will handle the close condition of SSE Emitter (if any).
     *
     * @param emitter SSE emitter which operations based on.
     */
    void execute(SseEmitter emitter);

    /**
     * Action which will be invoked when SSE Emitter closed {@link SseEmitter#onCompletion(Runnable)}.
     */
    void cleanUp();
}
