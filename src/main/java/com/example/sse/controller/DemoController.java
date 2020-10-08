package com.example.sse.controller;

import com.example.sse.core.SseEngine;
import com.example.sse.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public SseEmitter subscribe(@RequestParam String key) {
        Supplier<Page<Person>> supplier = getSupplier();
        Predicate<Page<Person>> predicate = getPredicate();
        SseEngine<Page<Person>> emitter = SseEngine.with(supplier, predicate, key);
        emitter.start();
        return emitter.get();
    }

    private Predicate<Page<Person>> getPredicate() {
        return persons -> persons.stream().anyMatch(person -> person.getStatus().equals(Status.SUCCESS.name()));
    }

    private Supplier<Page<Person>> getSupplier() {
        return () -> new PageImpl<>(Collections.singletonList(Person.of("DUMMY", Status.SUCCESS.name())));
    }
}

