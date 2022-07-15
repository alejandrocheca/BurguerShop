package com.example.demo.domain.orderDomain;
import com.example.demo.core.functionalInterfaces.FindById;

import java.util.UUID;
import reactor.core.publisher.Mono;

public class OrderWriteRepository extends FindById<Order, UUID>{
    public Mono<Order> save(Order order, Boolean isNew);
    public Mono<Void> delete(Order order);
}
