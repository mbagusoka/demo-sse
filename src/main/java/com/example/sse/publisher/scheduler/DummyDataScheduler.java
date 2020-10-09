package com.example.sse.publisher.scheduler;

import com.example.sse.core.SsePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DummyDataScheduler {

    private final SsePublisher<String> publisher;

    @Scheduled(fixedRate = 2000L)
    public void publish() {
        publisher.publish(String.format("Dummy Data: [%s]", UUID.randomUUID().toString()));
    }
}
