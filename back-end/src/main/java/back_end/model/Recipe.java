package back_end.model;

import java.util.Map;

public class Recipe {
    Map<Ingredient, Unit> ingredients;
    String name;
    String description;
    String instructions;
    String notes;
    String imageUrl;
    int servings;
    Double totalCalories;

    public Recipe(String name, String instructions, int servings, Map<Ingredient, Unit> ingredients) {
        this.name = name.toLowerCase();
        this.instructions = instructions;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Map<Ingredient, Unit> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getNotes() {
        return notes;
    }

    public int getServings() {
        return servings;
    }

    /*
     * Clears and recalculates the total calories for the recipe based on the ingredients and their quantities.
     */
    public Double calculateTotalCalories() {
        totalCalories = 0.0;

        for (Map.Entry<Ingredient, Unit> entry : ingredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Unit quantity = entry.getValue();

            if (ingredient != null && quantity != null) {
                if (! quantity.getName().equals(ingredient.getServingUnit().getName())) {
                    quantity = quantity.convertTo(ingredient.getServingUnit().getName());
                }

                double caloriesForIngredient = (quantity.getAmount() * ingredient.getCalories()) / ingredient.getServingSize();
                totalCalories += caloriesForIngredient;
            }
        }

        return totalCalories;
    }

}
