package hamburgueseria.backend.application.orderApplication;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class orderDTO {
    private UUID id;
    private UUID userId;
    private UUID HamburguerId;
    private OrderState state;
    private String address;
}
