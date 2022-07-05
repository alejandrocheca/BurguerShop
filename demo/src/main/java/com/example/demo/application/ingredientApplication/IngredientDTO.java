package hamburgueseria.backend.application.ingredientApplication;
import  hamburgueseria.backend.domainingredientDomain.IngredientType;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class IngredientDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
    private IngredientType type;
}
