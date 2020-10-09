package com.example.sse.single.service;

import com.example.sse.core.SseProcedure;

public interface SseProcedureProvider<T> {

    SseProcedure get(T param);
}
