package com.example.demo.domain.HamburguerDomain;
import com.example.demo.core.EntityBase;
import com.example.demo.domain.ingredientDomain.Ingredient;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "hamburguer")
@Setter
public class Hamburguer extends EntityBase{
    @NotBlank
    private String name;
    private String image;
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();
    private BigDecimal price = new BigDecimal(0.00);
    private final static BigDecimal IngredientProfit =  new BigDecimal(1.20);

    public Ingredient addIngredient(Ingredient ingredient){
        if(ingredient.getId() != null){
        ingredients.add(ingredient);
        price = price.add(ingredient.getPrice().multiply(IngredientProfit));
        price = price.setScale(2, RoundingMode.HALF_EVEN);}
        return ingredient;
    }
    public Hamburguer setPrice(){
        BigDecimal price = new BigDecimal(0.00);
        BigDecimal profit = new BigDecimal(1.20);

        for(Ingredient ing : ingredients){
            price = price.add(ing.getPrice());
        }
        price = price.multiply(profit);
        price = price.setScale(2, RoundingMode.HALF_EVEN);

        this.price= price;
        return this;
    }
    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
    }
    @Override
    public String toString() {
        return String.format("Hamburguer {id: %s, name: %s, price: %s, with ingredients:[%s]}", this.getId(), this.getName(), this.getPrice(), this.getIngredients().toString());
    }
}
