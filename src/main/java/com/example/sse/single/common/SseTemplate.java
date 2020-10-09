package com.example.sse.single.common;

import com.example.sse.core.SseOperations;
import com.example.sse.core.SseProcedure;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * A template for executing SSE operation.
 */
public class SseTemplate implements SseOperations {

    /**
     * Set of operations which must be implemented to execute SSE operation {@link SseProcedure}.
     */
    private final SseProcedure sseProcedure;

    private final SseEmitter emitter;

    /**
     * This is default timeout, can be override with {@link #valueOf(SseProcedure, long)}.
     * When timeout reached, the SSE emitter will be automatically closed
     * and method {@link SseEmitter#onCompletion(Runnable)} will be invoked.
     */
    private long timeout = 60000L;

    private SseTemplate(SseProcedure sseProcedure) {
        this.sseProcedure = sseProcedure;
        this.emitter = invoke(sseProcedure);
    }

    private SseTemplate(SseProcedure sseProcedure, long timeout) {
        this.sseProcedure = sseProcedure;
        this.timeout = timeout;
        this.emitter = invoke(sseProcedure);
    }

    public static SseTemplate valueOf(SseProcedure sseProcedure) {
        return new SseTemplate(sseProcedure);
    }

    public static SseTemplate valueOf(SseProcedure sseProcedure, long timeout) {
        return new SseTemplate(sseProcedure, timeout);
    }

    private SseEmitter invoke(SseProcedure sseProcedure) {
        SseEmitter sseEmitter = new SseEmitter(timeout);
        sseEmitter.onCompletion(sseProcedure::cleanUp);
        sseEmitter.onTimeout(sseProcedure::cleanUp);
        return sseEmitter;
    }

    @Override
    public void start() {
        sseProcedure.execute(emitter);
    }

    @Override
    public SseEmitter get() {
        return emitter;
    }

    public long getTimeout() {
        return timeout;
    }
}
