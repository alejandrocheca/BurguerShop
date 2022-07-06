package hamburgueseria.backend.application.orderApplication;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class orderApplication {
    public Mono<OrderDTO> add(UUID userId, CreateOrUpdateOrderDTO dto);
    public Mono<OrderDTO> get(String id);
    public Mono<Void> update(String id, CreateOrUpdateOrderDTO dto);
    public Mono<Void> delete(String id);
    public Flux<OrderDTO> getAll();
}
