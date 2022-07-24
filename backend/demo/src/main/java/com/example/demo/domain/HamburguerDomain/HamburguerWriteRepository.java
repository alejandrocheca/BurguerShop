package com.example.demo.domain.HamburguerDomain;
import Hamburgueseria.core.functionalInterfaces.ExistsByField;
import Hamburgueseria.core.functionalInterfaces.FindById;

import reactor.core.publisher.Mono;
import java.util.UUID;

public interface  HamburguerWriteRepository extends FindById<Hamburguer, UUID>, ExistsByField{
    public Mono<Hamburguer> save(Hamburguer hamburguer, Boolean isNew);
}
