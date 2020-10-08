package com.example.sse.controller;

import lombok.Value;

@Value(staticConstructor = "of")
public class Person {

    String name;
    String status;
}
