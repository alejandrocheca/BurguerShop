package com.example.demo.infraestructure.orderInfraestructure;
import Hamburrgueseria.backend.domain.orderDomain.Order;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
public interface OrderRepository extends ReactiveMongoRepository<Order, UUID>{
    
}
