package com.example.demo.domain.ingredientDomain;
import com.example.demo.core.EntityBase;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ingredient")
@Setter
@EqualsAndHashCode(callSuper=true)
public class Ingredient extends EntityBase{
    @NotBlank
    private String name;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal price;
    @NotNull
    private IngredientType type = IngredientType.regular;

    @Override
    public String toString() {
        return String.format("Ingredient {id: %s, name: %s, price %s}", this.getId(), this.getName(), this.getPrice());
    }
}
