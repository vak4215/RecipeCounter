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

    public Recipe(String name, String description, String instructions, String notes, String imageUrl, int servings, Double totalCalories, Map<Ingredient, Unit> ingredients) {
        this.name = name.toLowerCase();
        this.description = description;
        this.instructions = instructions;
        this.notes = notes;
        this.imageUrl = imageUrl;
        this.servings = servings;
        this.totalCalories = totalCalories;
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

    /*
     * Returns a string representation of the recipe in CSV format.
     */
    public String toStringCSVFormat() {
        // Format: name,description,instructions,notes,imageUrl,servings,total calories,ingredients
        return String.format("%s,\"%s\",\"%s\",\"%s\",%s,%d,%.2f,\"%s\"%n",
            name, description, instructions, notes, imageUrl, servings, totalCalories,
            ingredients.entrySet().stream()
                .map(entry -> String.format("%s;%s", entry.getKey().getName(), entry.getValue().toStringCSVFormat()))
                .reduce((a, b) -> a + "," + b).orElse(""));
    }
}
