package back_end.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import back_end.service.IngredientService;

public class RecipeTest {
    /*
     * Testing with this ingredient :
     * all-purpose flour,110.000000,0.250000,CUP
    */

    @Test
	void testCalculateTotalCaloriesInCups() {
        IngredientService ingredientService = new IngredientService();
        Map<Ingredient, Unit> ingredients = new HashMap<>();
        Ingredient testIngredient = ingredientService.getIngredientByName("all-purpose flour");
        Unit quantity = Unit.CUP;
        quantity.setAmount(1);
        ingredients.put(testIngredient, quantity);

        Recipe testRecipe = new Recipe("testRecipe", "testing", 4, ingredients);

        assertThat(testRecipe.calculateTotalCalories()).isEqualTo(440);
	}

    @Test
	void testCalculateTotalCaloriesInTablespoons() {
        IngredientService ingredientService = new IngredientService();
        Map<Ingredient, Unit> ingredients = new HashMap<>();
        Ingredient testIngredient = ingredientService.getIngredientByName("all-purpose flour");
        Unit quantity = Unit.TABLESPOON;
        quantity.setAmount(16);
        ingredients.put(testIngredient, quantity);

        Recipe testRecipe = new Recipe("testRecipe", "testing", 4, ingredients);

        assertThat(testRecipe.calculateTotalCalories()).isEqualTo(440);
	}

    @Test
	void testCalculateTotalCaloriesInTeaspoons() {
        IngredientService ingredientService = new IngredientService();
        Map<Ingredient, Unit> ingredients = new HashMap<>();
        Ingredient testIngredient = ingredientService.getIngredientByName("all-purpose flour");
        Unit quantity = Unit.TEASPOON;
        quantity.setAmount(48);
        ingredients.put(testIngredient, quantity);

        Recipe testRecipe = new Recipe("testRecipe", "testing", 4, ingredients);

        assertThat(testRecipe.calculateTotalCalories()).isEqualTo(440);
	}
}
