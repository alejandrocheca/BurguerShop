package hamburgueseria.backend.application.orderApplication;
import java.util.UUID;

import hamburgueseria.backend.domain.OrderDomain.OrderState;
import lombok.Setter;
@Getter
@Setter
public class createOrUpdateOrderDTO {
    private UUID HamburguerId;
    private OrderState state;
    private String address;
}
