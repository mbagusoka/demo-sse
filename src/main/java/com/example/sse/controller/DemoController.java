package com.example.sse.controller;

import com.example.sse.core.SseEngine;
import com.example.sse.enums.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.function.Predicate;
import java.util.function.Supplier;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public SseEmitter subscribe(@RequestParam String key) {
        Supplier<String> supplier = Status.SUCCESS::name;
        Predicate<String> predicate = token -> Status.SUCCESS.name().equals(token);
        SseEngine<String> emitter = SseEngine.with(supplier, predicate, key);
        emitter.start();
        return emitter.get();
    }
}
