package com.example.sse.single.controller;

import com.example.sse.core.SseProcedure;
import com.example.sse.single.common.SseTemplate;
import com.example.sse.single.service.SseProcedureProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/single")
@RequiredArgsConstructor
public class SingleController {

    private final SseProcedureProvider<String> provider;

    @GetMapping
    public SseEmitter get(@RequestParam String name) {
        SseProcedure procedure = provider.get(name);
        SseTemplate sseTemplate = SseTemplate.valueOf(procedure, 5000L);
        sseTemplate.start();
        return sseTemplate.get();
    }
}
