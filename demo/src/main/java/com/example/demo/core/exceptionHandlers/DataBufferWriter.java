package com.example.demo.core.exceptionHandlers;
import Hamburgueseria.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataBufferWriter {
    private final ObjectMapper objectMapper;

    public <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        return httpResponse.writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(object));
                    } catch (Exception ex) {
                        log.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }
}
